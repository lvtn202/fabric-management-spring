package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Return;
import com.example.lvtn.dom.ReturnSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ReturnRepositoryCustomImpl implements ReturnRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Return> findReturns(Long pageIndex, Long pageSize) {
        try {
            String sql = "select r from " + Return.class.getName() + " r ";
            Query query = entityManager.createQuery(sql, Return.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Return> findReturnsByReturnSlipId(Long returnSlipId, Long pageIndex, Long pageSize) {
        try {
            String sql = "select r from " + Return.class.getName() + " r where  r.returnSlip.id = :returnSlipId";
            Query query = entityManager.createQuery(sql, Return.class);
            query.setParameter("returnSlipId", returnSlipId);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
