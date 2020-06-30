package com.idanch.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/")
public class LogSearchQueryFilter implements Filter {
    public static final Logger log = LoggerFactory.getLogger(LogSearchQueryFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String searchQuery = req.getParameter("searchQuery");
        if (searchQuery != null) {
            log.info("The user searched for: '" + searchQuery + "'");
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
