package com.example.lvtn.service;

import com.example.lvtn.dom.Debt;
import com.example.lvtn.utils.InternalException;

import java.sql.Timestamp;
import java.util.List;

public interface DebtService {
    List<Debt> findAll() throws InternalException;

    Debt createDebt(Long type, Long idTransaction, Double amount, Timestamp createDate, Double oldTotal) throws InternalException;

    String createData() throws InternalException;
}
