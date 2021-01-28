package top.banner.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.banner.security.PermitAllUrlProperties;
import top.banner.security.RestHttpSessionIdResolver;
import top.banner.security.handler.*;
import top.banner.security.service.DefaultUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsService userDetailsService;
    /**
     * 登出成功的处理
     */
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    /**
     * 登录成功的处理
     */
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    /**
     * 登出成功的处理
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    /**
     * 未登录的处理
     */
    @Autowired
    private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;
    /**
     * 超时处理
     */
    @Autowired
    private InvalidSessionHandler invalidSessionHandler;
    /**
     * 顶号处理
     */
    @Autowired
    private SessionInformationExpiredHandler sessionInformationExpiredHandler;
    /**
     * 登录用户没有权限访问资源
     */
    @Autowired
    private LoginUserAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    /**
     * 用户加密方式
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        // 委托密码编码器，默认使用是 bcrypt 算法加密
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 设置默认加密算法 TODO 测试方便，默认不加密
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        passwordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return passwordEncoder;
    }

    /**
     * 配置认证方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * http相关的配置，包括登入登出、异常处理、会话管理等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                // 放行接口
                .antMatchers(permitAllUrlProperties.getIgnoreUrls().toArray(new String[0])).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling()
                //匿名用户访问无权限资源时的异常处理
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
                //登录用户没有权限访问资源
                .accessDeniedHandler(accessDeniedHandler)
                // 登入
                .and().formLogin().permitAll()//允许所有用户
                //登录成功处理逻辑
                .successHandler(loginSuccessHandler)
                //登录失败处理逻辑
                .failureHandler(loginFailureHandler)
                // 登出
                .and().logout().permitAll()//允许所有用户
                //登出成功处理逻辑
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(RestHttpSessionIdResolver.AUTH_TOKEN)
                // 会话管理// 超时处理
                .and().sessionManagement().invalidSessionStrategy(invalidSessionHandler)
                //同一账号同时登录最大用户数
                .maximumSessions(1)
                // 顶号处理
                .expiredSessionStrategy(sessionInformationExpiredHandler);
        // 资源服务器，需要跳过登录登出filter
//        http.formLogin().disable().logout().disable();
    }

}