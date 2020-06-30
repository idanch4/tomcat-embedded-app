package com.idanch.filters.wrappers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BanVegetablesServletRequestWrapper extends HttpServletRequestWrapper {
    private String searchQuery;

    public BanVegetablesServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String getParameter(String name) {
        if (name.equals("searchQuery")) {
            return searchQuery;
        } else {
            return super.getParameter(name);
        }
    }
}