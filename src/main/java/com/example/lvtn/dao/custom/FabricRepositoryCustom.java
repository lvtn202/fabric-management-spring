package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.DyehouseDTO;

import java.util.List;

public interface FabricRepositoryCustom {
    List<Fabric> findExportedFabrics();

    List<Fabric> findFabricsByDyehouseId (Long dyehouseId);
}
