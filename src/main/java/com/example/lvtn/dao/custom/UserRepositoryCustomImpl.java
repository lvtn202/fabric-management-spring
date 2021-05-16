package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
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

    @Override
    public List<User> findUsersByUsername(String username) {
        try {
            String sql = "select u from " + User.class.getName() + " u "
                    + "where u.username = :username";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("username", username);
            return  query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        try {
            String sql = "select u from " + User.class.getName() + " u "
                    + "where u.email = :email";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("email", email);
            return  query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User findUsersById(Long userId) {
        try {
            String sql = "select u from " + User.class.getName() + " u "
                    + "where u.id = :userId";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("userId", userId);
            return (User) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> findUsersWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select u from " + User.class.getName() + " u "
                    + "order by u.id ";
            Query query = entityManager.createQuery(sql, User.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
