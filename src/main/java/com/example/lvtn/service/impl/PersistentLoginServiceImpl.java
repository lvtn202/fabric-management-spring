package com.example.lvtn.service.impl;

import com.example.lvtn.dao.PersistentLoginRepository;
import com.example.lvtn.dom.PersistentLogin;
import com.example.lvtn.service.PersistentLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistentLoginServiceImpl implements PersistentLoginService {
    @Autowired
    PersistentLoginRepository persistentLoginRepository;

    @Override
    public List<PersistentLogin> findAll() {
        return persistentLoginRepository.findAll();
    }
}
