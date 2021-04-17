package com.example.lvtn.service;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.StatisticCompletedFabric;
import com.example.lvtn.dto.StatisticExportedFabric;
import com.example.lvtn.dto.StatisticRawFabric;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface FabricService {
    List<Fabric> findAll();

    List<StatisticRawFabric> findStatisticRawFabrics(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;

    List<StatisticCompletedFabric> findStatisticCompletedFabricsInDyehouse(Long dyehouseId, Double startDate, Double endDate, Long pageIndex, Long pageSize) throws InternalException;

    List<StatisticExportedFabric> findStatisticExportedFabrics(Long pageIndex, Long pageSize) throws InternalException;
}
