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

    @Override
    public List<Fabric> findCompletedFabricsByDyeBatchId(Long dyeBatchId) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "' and f.dyeBatch.id = :dyeBatchId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("dyeBatchId", dyeBatchId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Fabric findFabricById(Long fabricId) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.id = :fabricId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricId", fabricId);
            return (Fabric) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findExportedFabricsInDyehouse(Long dyehouseId) {
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
    public List<Fabric> findRawFabricsByFabricType(String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.IN_RAW.toString() + "' and f.fabricType.type = :fabricType";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricType", fabricType);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findExportedFabricsByFabricType(String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.EXPORTED.toString() + "' "
                    + "and f.fabricType.type = :fabricType ";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricType", fabricType);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findExportedFabricsInDyehouseByFabricType(Long dyehouseId, String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.EXPORTED.toString() + "' "
                    + "and f.dyehouse.id = :dyehouseId and f.fabricType.type = :fabricType ";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("dyehouseId", dyehouseId);
            query.setParameter("fabricType", fabricType);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findAllCompletedFabrics(Long pageIndex, Long pageSize) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "'";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findAllCompletedFabricsByDyehouseId(Long dyehouseId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "' "
                    + "and f.dyehouse.id = :dyehouseId ";
            Query query = entityManager.createQuery(sql, Fabric.class);
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
    public List<Fabric> findCompletedFabrics() {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "'";
            Query query = entityManager.createQuery(sql, Fabric.class);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findCompletedFabricsByFabricType(String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString() + "' and f.fabricType.type = :fabricType";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricType", fabricType);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findRawFabricsByDyehouseIdAndFabricType(Long dyehouseId, String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.EXPORTED.toString()
                    + "' and f.fabricType.type = :fabricType "
                    + "and f.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricType", fabricType);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Fabric> findCompletedFabricsByDyehouseIdAndFabricType(Long dyehouseId, String fabricType) {
        try {
            String sql = "select f from " + Fabric.class.getName() + " f "
                    + "where f.status = '" + FabricStatus.COMPLETED.toString()
                    + "' and f.fabricType.type = :fabricType "
                    + "and f.dyehouse.id = :dyehouseId";
            Query query = entityManager.createQuery(sql, Fabric.class);
            query.setParameter("fabricType", fabricType);
            query.setParameter("dyehouseId", dyehouseId);
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
