package com.example.lvtn.service;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.StatisticFabric;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface FabricService {
    List<Fabric> findAll();

    List<StatisticFabric> findStatisticFabrics(Long dyehouseId, Long startDate, Long endDate, Long pageIndex, Long pageSize) throws InternalException;
}
