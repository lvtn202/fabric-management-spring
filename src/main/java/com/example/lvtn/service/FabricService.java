package com.example.lvtn.service;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.FabricDTO;
import com.example.lvtn.dto.StatisticCompletedFabric;
import com.example.lvtn.dto.StatisticRawFabric;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface FabricService {
    List<Fabric> findAll();

    List<StatisticRawFabric> findStatisticRawFabrics(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;

    List<StatisticCompletedFabric> findStatisticCompletedFabricsInDyehouse(Long dyehouseId, Double startDate, Double endDate, Long pageIndex, Long pageSize) throws InternalException;

    List<FabricDTO> findFabricsByDyeBatchId(Long dyeBatchId) throws InternalException;

    String getRawLength(Long fabricId) throws InternalException;

    boolean isRawFabric(Long fabricId) throws InternalException;

    List<FabricDTO> findRawFabricDTOsByFabricType(String fabricType) throws InternalException;

    List<FabricDTO> findExportedFabricDTOsByFabricTypeAndColor(Long dyehouseId, String fabricType, String color) throws InternalException;
}
