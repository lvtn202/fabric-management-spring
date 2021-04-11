package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.Order;
import com.example.lvtn.utils.FabricStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Order> findOrdersByDyehouseId(Long dyehouseId) {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Order> findOrdersWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select o from " + Order.class.getName() + " o";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Order> findOrdersByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Order.class);
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
    public Order findOrderById(Long id) {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.id = :id";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setParameter("id", id);
            return (Order) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
