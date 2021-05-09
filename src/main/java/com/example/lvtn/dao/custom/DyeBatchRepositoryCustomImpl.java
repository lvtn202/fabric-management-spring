package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.DyeBatch;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DyeBatchRepositoryCustomImpl implements DyeBatchRepositoryCustom {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<DyeBatch> findDyeBatchesWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select d from " + DyeBatch.class.getName() + " d order by d.dyeDate desc ";
            Query query = entityManager.createQuery(sql, DyeBatch.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<DyeBatch> findDyeBatchesByImportSlipIdWithPaging(Long importSlipId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select d from " + DyeBatch.class.getName() + " d "
                    + "where d.importSlip.id = :importSlipId order by d.dyeDate desc ";
            Query query = entityManager.createQuery(sql, DyeBatch.class);
            query.setParameter("importSlipId", importSlipId);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public DyeBatch findDyeBatchById(Long dyeBatchId) {
        try {
            String sql = "select d from " + DyeBatch.class.getName() + " d "
                    + "where d.id = :dyeBatchId order by d.dyeDate desc ";
            Query query = entityManager.createQuery(sql, DyeBatch.class);
            query.setParameter("dyeBatchId", dyeBatchId);
            return (DyeBatch) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
