package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.ImportSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ImportSlipRepositoryCustomImpl implements ImportSlipRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<ImportSlip> findImportSlipsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select i from " + ImportSlip.class.getName() + " i "
                    + "where i.order.id = :orderId";
            Query query = entityManager.createQuery(sql, ImportSlip.class);
            query.setParameter("orderId", orderId);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}