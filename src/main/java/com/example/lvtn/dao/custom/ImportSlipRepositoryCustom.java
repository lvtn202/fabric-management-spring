package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface ImportSlipRepositoryCustom {
    public List<ImportSlip> findImportSlipsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize);

    List<ImportSlip> findRecentImportSlips(Long pageIndex, Long pageSize);

    List<ImportSlip> findRecentImportSlipsInDyehouse(Long dyehouseId, Long pageIndex, Long pageSize);

    ImportSlip findImportSlipById(Long id) throws InternalException;
}
