package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.utils.FabricStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FabricRepositoryCustomImpl implements FabricRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Fabric> findExportedFabrics() {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.EXPORTED.toString() + "'";
            Query query = entityManager.createQuery(sql, Fabric.class);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findRawFabrics() {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.IN_RAW.toString() + "'";
            Query query = entityManager.createQuery(sql, Fabric.class);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findRawFabricsByDyehouseId(Long dyehouseId) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.EXPORTED.toString() + "' and f.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findCompletedFabricsByDyehouseId(Long dyehouseId) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "' and f.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
