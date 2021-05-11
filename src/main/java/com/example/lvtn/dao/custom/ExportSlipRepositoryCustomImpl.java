package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.dom.ImportSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ExportSlipRepositoryCustomImpl implements ExportSlipRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<ExportSlip> findRecentExportSlips(Long pageIndex, Long pageSize) {
        try {
            String sql = "select e from " + ExportSlip.class.getName() + " e "
                    + "order by e.createDate desc ";
            Query query = entityManager.createQuery(sql, ExportSlip.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<ExportSlip> findRecentExportSlipsInDyehouse(Long dyehouseId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select e from " + ExportSlip.class.getName() + " e "
                    + "where e.dyehouse.id = :dyehouseId order by e.createDate desc ";
            Query query = entityManager.createQuery(sql, ExportSlip.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
