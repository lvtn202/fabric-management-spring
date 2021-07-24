package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Debt;
import com.example.lvtn.dom.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

public class DebtRepositoryCustomImpl implements DebtRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Debt> findDebtsByDyehouseIdInRange(Long dyehouseId, Timestamp startDate, Timestamp endDate) {
        try {
            String sql = "select d from " + Debt.class.getName() + " d "
                    + "where d.dyehouse.id = :dyehouseId "
                    + "and d.createDate >= :startDate and d.createDate < :endDate "
                    + "order by d.createDate asc ";
            Query query = entityManager.createQuery(sql, Debt.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
