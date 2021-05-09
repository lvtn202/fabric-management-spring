package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Order;
import com.example.lvtn.dom.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Payment> findPaymentsWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select p from " + Payment.class.getName() + " p order by p.createDate desc ";
            Query query = entityManager.createQuery(sql, Payment.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Payment> findPaymentsByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select p from " + Payment.class.getName() + " p "
                    + "where p.dyehouse.id = :dyehouseId order by p.createDate desc ";
            Query query = entityManager.createQuery(sql, Payment.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Payment> findTotalRecentPayment(Long period) {
        try {
            String sql = "select p from " + Payment.class.getName() + " p "
                    + "where p.createDate >= :period order by p.createDate desc ";
            Query query = entityManager.createQuery(sql, Payment.class);
            query.setParameter("period",  new Timestamp(System.currentTimeMillis() - period * 86400000));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Payment> findTotalRecentPaymentInDyehouse(Long dyehouseId, Long period) {
        try {
            String sql = "select p from " + Payment.class.getName() + " p "
                    + "where p.dyehouse.id = :dyehouseId "
                    + "and p.createDate >= :period order by p.createDate desc ";
            Query query = entityManager.createQuery(sql, Payment.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setParameter("period",  new Timestamp(System.currentTimeMillis() - period * 86400000));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
