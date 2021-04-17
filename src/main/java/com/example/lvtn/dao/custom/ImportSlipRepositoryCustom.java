package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.ImportSlip;

import java.util.List;

public interface ImportSlipRepositoryCustom {
    public List<ImportSlip> findImportSlipsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize);
}