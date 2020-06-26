package com.idanch.data.factories;

import com.idanch.data.MenuDaoImpl;
import com.idanch.data.interfaces.MenuDao;

public class MenuDaoFactory {
    private static MenuDao menuDao;

    public static MenuDao getMenuDao() {
        if (menuDao != null) {
            return menuDao;
        }
        return new MenuDaoImpl();
    }
}
