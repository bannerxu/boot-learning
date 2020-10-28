package top.banner.security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import top.banner.security.config.SwaggerConfig;
import top.banner.security.service.LoginService;
import top.banner.security.service.UserService;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author XGL
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    //    @Resource
//    private PasswordEncoder passwordEncoder;
    @Resource
    private LoginService loginService;

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(
                preAuthAuthProvider(),
                daoAuthenticationProvider()
        ));
    }

    // 客户端授权--预授权模块
    // 一个可以提供认证的处理器
    // AuthenticationProvider
    // 实际上一点都不复杂
    // 首先建立一个预授权认证器提供者，它支持特有的认证
    // 再以此建立一个认证中心；当然可以添加更多的认证中心；同时将可以生成这种认证结果的Filter加入
    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthAuthProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userService);
        return provider;
    }


    @Bean
    public AbstractPreAuthenticatedProcessingFilter preAuthFilter() {
        ClientTokenFilter filter = new ClientTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setContinueFilterChainOnUnsuccessfulAuthentication(true);
        filter.setCheckForPrincipalChanges(true);
        return filter;
    }


    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(loginService);
        return provider;
    }

//
//    @Bean
//    open fun usernamePasswordAuthenticationFilter(): UsernamePasswordAuthenticationFilter {
//        val filter = PlatformManageAuthenticationFilter()
//        filter.setPostOnly(true)
//        filter.setContinueChainBeforeSuccessfulAuthentication(false)
//        filter.setAuthenticationManager(authenticationManager())
//        filter.setAuthenticationSuccessHandler { request, response, authentication ->
//                val login = authentication.principal as UserDetails
//            val ip = ServletUtils.clientIpAddress(request)
//            applicationEventPublisher.publishEvent(ManageLoginEvent(login, ip))
//            response.contentType = "application/json"
//            response.writer.use { writer ->
//                    writer.write(
//                            objectMapper.writeValueAsString(
//                                    managerLoginData(ServletUtils.clientIpAddress(request), login, userService, loginService)
//                            )
//                    )
//                writer.flush()
//            }
////            response.sendError(200)
//        }
//        return filter
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .antMatchers("/druid/**")
                .antMatchers(SwaggerConfig.AUTH_LIST);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.headers().frameOptions().sameOrigin();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**")
                .authorizeRequests();
        registry
                .antMatchers("/login").permitAll()
                // 其他必须接受保护
                .antMatchers("/**").authenticated()
                .and()
                .addFilter(preAuthFilter())
//                .addFilter(usernamePasswordAuthenticationFilter())
//                .addFilter(groupManageUsernamePasswordAuthenticationFilter())
//                .addFilter(channelAuthenticationFilter())
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint());
    }

    //    // 客户端授权--预授权模块 结束
//    override fun configure(web: WebSecurity) {
//        super.configure(web)
//        web
//
////                .addSecurityFilterChainBuilder()
//                .ignoring()
//                .antMatchers("/druid/**")
//                .antMatchers("/q/**")
//                .antMatchers("/echo/**")
//                .antMatchers("/openfire/**")
//                .antMatchers("/offlineNotice")
//                //测试回调接口用到的
//                .antMatchers("/test/**")
//        if (environment.acceptsProfiles("staging") || environment.acceptsProfiles("test")) {
//            web.ignoring().antMatchers("/updateOrder/**")
//        }
//    }
}
