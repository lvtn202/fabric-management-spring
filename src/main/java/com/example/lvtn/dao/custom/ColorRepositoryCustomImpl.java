package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Color;
import com.example.lvtn.dom.Dyehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ColorRepositoryCustomImpl implements ColorRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Color> findColorsByFabricTypeId(Long fabricTypeId) {
        try {
            String sql = "select c from " + Color.class.getName() + " c where c.fabricType.id = :fabricTypeId";
            Query query = entityManager.createQuery(sql, Color.class);
            query.setParameter("fabricTypeId", fabricTypeId);
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
