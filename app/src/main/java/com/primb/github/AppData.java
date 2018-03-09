package com.primb.github;

import com.primb.github.dao.AuthUser;
import com.primb.github.mvp.model.User;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;

import java.util.Locale;

/**
 * @author Chen
 * @date 2018/3/8
 * 功能描述：
 */

public enum AppData {
    INSTANCE;

    @AutoAccess(dataName = "appData_loggedUser")
    User loggedUser;
    @AutoAccess(dataName = "appData_authUser")
    AuthUser authUser;
    @AutoAccess(dataName = "appData_systemDefaultLocal")
    Locale systemDefaultLocal;
}
