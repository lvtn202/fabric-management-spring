package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.QualifierEntry;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DyehouseRepositoryCustomImpl implements DyehouseRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Dyehouse> findDyehousesWithNameAndPaging(String dyehouseName, Long pageIndex, Long pageSize) {
        try {
            String sql = "select d from " + Dyehouse.class.getName() + " d "
                    + "where d.name = :dyehouseName";
            Query query = entityManager.createQuery(sql, Dyehouse.class);
            query.setParameter("dyehouseName", dyehouseName);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Dyehouse> findDyehousesWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select d from " + Dyehouse.class.getName() + " d";
            Query query = entityManager.createQuery(sql, Dyehouse.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Dyehouse findDyehouseById(Long id) {
        try {
            String sql = "select d from " + Dyehouse.class.getName() + " d where d.id = :id";
            Query query = entityManager.createQuery(sql, Dyehouse.class);
            query.setParameter("id", id);
            return (Dyehouse) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
