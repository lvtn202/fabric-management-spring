package com.example.lvtn.service;

import com.example.lvtn.dom.PersistentLogin;

import java.util.List;

public interface PersistentLoginService {
    List<PersistentLogin> findAll();
}
