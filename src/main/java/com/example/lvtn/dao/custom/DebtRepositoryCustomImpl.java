package com.example.lvtn.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class DebtRepositoryCustomImpl implements DebtRepositoryCustom{
    @Autowired
    EntityManager entityManager;

}
