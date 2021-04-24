package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Return;

import java.util.List;

public interface ReturnRepositoryCustom {
    List<Return> findReturns(Long pageIndex, Long pageSize);

    List<Return> findReturnsByReturnSlipId(Long returnSlipId, Long pageIndex, Long pageSize);
}
