package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Color;
import com.example.lvtn.dom.PersistentLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersistentLoginrepositoryCustomImpl implements PersistentLoginrepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public boolean isPersistentLoginExisted(Long userId) {
        try {
            String sql = "select p from " + PersistentLogin.class.getName() +
                    " p where p.user.id = :userId";
            Query query = entityManager.createQuery(sql, PersistentLogin.class);
            query.setParameter("userId", userId);
            List<PersistentLogin> listPersistentLogin = query.getResultList();
            if (listPersistentLogin.size() > 0){
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public PersistentLogin getCurrentPersistentLogin(Long userId) {
        try {
            String sql = "select p from " + PersistentLogin.class.getName() +
                    " p where p.user.id = :userId";
            Query query = entityManager.createQuery(sql, PersistentLogin.class);
            query.setParameter("userId", userId);
            return (PersistentLogin) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
