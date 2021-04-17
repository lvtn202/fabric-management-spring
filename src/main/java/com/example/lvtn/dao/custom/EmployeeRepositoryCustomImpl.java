package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom{
    @Autowired
    private EntityManager entityManager;

    public User findUserAccount(String userName) {
        try {
            String sql = "Select e from " + User.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("userName", userName);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
