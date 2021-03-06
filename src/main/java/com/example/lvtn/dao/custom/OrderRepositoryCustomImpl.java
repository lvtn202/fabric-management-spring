package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.Order;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.utils.OrderStatus;
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
    public List<Order> findOrders() {
        try {
            String sql = "select o from " + Order.class.getName() + " o order by o.createDate desc ";
            Query query = entityManager.createQuery(sql, Order.class);
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Order> findOrdersByDyehouseId(Long dyehouseId) {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.dyehouse.id = :dyehouseId order by o.createDate desc ";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Order findOrderById(Long id) throws InternalException {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.id = :id order by o.createDate desc ";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setParameter("id", id);
            return (Order) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException("ERROR_ID");
        }
    }

    @Override
    public List<Order> findOrdersOfDyehouseByFabricTypeAndColor(Long dyehouseId, String fabricType, String color) {
        try {
            String sql = "select o from " + Order.class.getName() + " o "
                    + "where o.status in ('" + OrderStatus.CREATED.toString()
                    + "','" + OrderStatus.IN_PROGRESS.toString() + "') "
                    + "and o.dyehouse.id = :dyehouseId "
                    + "and o.color.fabricType.type = :fabricType "
                    + "and o.color.name = :color order by o.createDate desc ";
            Query query = entityManager.createQuery(sql, Order.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setParameter("fabricType", fabricType);
            query.setParameter("color", color);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
