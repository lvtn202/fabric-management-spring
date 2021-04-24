package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dom.ReturnSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ReturnSlipRepositoryCustomImpl implements ReturnSlipRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<ReturnSlip> findReturnSlipsWithPaging(Long pageIndex, Long pageSize) {
        try {
            String sql = "select r from " + ReturnSlip.class.getName() + " r order by r.id";
            Query query = entityManager.createQuery(sql, ReturnSlip.class);
            query.setFirstResult((int) (pageIndex * pageSize));
            query.setMaxResults(Math.toIntExact(pageSize));
            return  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ReturnSlip findReturnSlipById(Long returnSlipId) {
        try {
            String sql = "select r from " + ReturnSlip.class.getName() + " r where  r.id = :returnSlipId";
            Query query = entityManager.createQuery(sql, ReturnSlip.class);
            query.setParameter("returnSlipId", returnSlipId);
            return (ReturnSlip) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
