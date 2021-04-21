package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.DyeBatch;

import java.util.List;

public interface DyeBatchRepositoryCustom {
    List<DyeBatch> findDyeBatchesWithPaging(Long pageIndex, Long pageSize);

    List<DyeBatch> findDyeBatchesByImportSlipIdWithPaging(Long importSlipId, Long pageIndex, Long pageSize);

    DyeBatch findDyeBatchById(Long dyeBatchId);
}
