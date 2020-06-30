package com.idanch.filters;

import com.idanch.filters.wrappers.BanVegetablesServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/searchResults")
public class BanVegetablesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String searchQuery = req.getParameter("searchQuery");
        if (searchQuery != null && searchQuery.toLowerCase().contains("veg")) {
            BanVegetablesServletRequestWrapper requestWrapper =
                    new BanVegetablesServletRequestWrapper((HttpServletRequest) req);
            requestWrapper.setSearchQuery("pizza");
            chain.doFilter(requestWrapper, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
