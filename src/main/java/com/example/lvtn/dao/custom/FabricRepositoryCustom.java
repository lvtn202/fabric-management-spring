package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.DyehouseDTO;

import java.util.List;

public interface FabricRepositoryCustom {
    List<Fabric> findExportedFabrics();

    List<Fabric> findRawFabrics();

    List<Fabric> findRawFabricsByDyehouseId(Long dyehouseId);

    List<Fabric> findCompletedFabricsByDyehouseId(Long dyehouseId);

    List<Fabric> findCompletedFabricsByDyeBatchId(Long dyeBatchId);

    Fabric findFabricById(Long fabricId);

    List<Fabric> findExportedFabricsInDyehouse(Long dyehouseId);

    List<Fabric> findRawFabricsByFabricType(String fabricType);

    List<Fabric> findExportedFabricsByFabricTypeAndColor(String fabricType, String color);

    List<Fabric> findExportedFabricsInDyehouseByFabricTypeAndColor(Long dyehouseId, String fabricType, String color);

}
