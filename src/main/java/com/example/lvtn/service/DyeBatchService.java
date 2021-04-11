package com.example.lvtn.service;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface DyeBatchService {
    List<DyeBatch> findAll();

    List findDyeBatches(Long importSlipId, Long pageIndex, Long pageSize) throws InternalException;
}
