package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.PersistentLogin;

public interface PersistentLoginrepositoryCustom {
    boolean isPersistentLoginExisted(Long userId);

    PersistentLogin getCurrentPersistentLogin(Long userId);

    PersistentLogin getCurrentPersistentLoginByToken(String token);
}
