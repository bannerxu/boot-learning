package top.banner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banner.core.CurlBuilder;
import top.banner.core.Options;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(XssFilter.class);


    @Override
    public void init(javax.servlet.FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        log.info("uri:{}", req.getRequestURI());
        String contentType = req.getContentType();
        if ((contentType != null) && (contentType.toLowerCase().startsWith("multipart/"))) {
            log.info("curl 暂不支持 表单中进行文件上传");
            chain.doFilter(req, resp);
        } else {
            MyRequestWrapper wrapper = new MyRequestWrapper(req);
            CurlBuilder curl = new CurlBuilder(wrapper, wrapper.getBodyStr(), Options.EMPTY);
            log.info(curl.toString());
            // xss 过滤
            chain.doFilter(wrapper, resp);
        }

    }

    @Override
    public void destroy() {

    }
}

