package com.example.lvtn.service;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface DyeBatchService {
    List<DyeBatch> findAll();

    List findDyeBatches(Long importSlipId, Long pageIndex, Long pageSize) throws InternalException;

    ModelMap findDetailDyeBatchById(Long dyeBatchId) throws InternalException;
}
