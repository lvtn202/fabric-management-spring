package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.ExportSlip;

import java.util.List;

public interface ExportSlipRepositoryCustom {
    List<ExportSlip> findRecentExportSlips(Long pageIndex, Long pageSize);

    List<ExportSlip> findRecentExportSlipsInDyehouse(Long dyehouseId, Long pageIndex, Long pageSize);
}
