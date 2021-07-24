package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Debt;

import java.sql.Timestamp;
import java.util.List;

public interface DebtRepositoryCustom {
    List<Debt> findDebtsByDyehouseIdInRange(Long dyehouseId, Timestamp startDate, Timestamp endDate);
}
