package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.Order;
import com.example.lvtn.utils.FabricStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
}
