package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.ReturnSlip;

import java.util.List;

public interface ReturnSlipRepositoryCustom {
    List<ReturnSlip> findReturnSlipsWithPaging(Long pageIndex, Long pageSize);

    ReturnSlip findReturnSlipById(Long returnSlipId);
}
