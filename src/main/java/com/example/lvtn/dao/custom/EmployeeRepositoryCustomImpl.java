package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Employee;
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

    public Employee findUserAccount(String userName) {
        try {
            String sql = "Select e from " + Employee.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            Query query = entityManager.createQuery(sql, Employee.class);
            query.setParameter("userName", userName);

            return (Employee) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
