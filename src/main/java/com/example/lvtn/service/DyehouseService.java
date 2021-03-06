package com.example.lvtn.service;

import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dto.CreateDyehouseForm;
import com.example.lvtn.dto.DyehouseDTO;
import com.example.lvtn.dto.UpdateDyehouseForm;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface DyehouseService {
    List<Dyehouse> findAll();

    List<DyehouseDTO> findDyehouseDTOsByNameWithPaging(String dyehouseName, Long pageIndex, Long pageSize) throws InternalException;

    ModelMap findDetailDyehouseById(Long id) throws InternalException;

    ModelMap updateDyehouse (UpdateDyehouseForm updateDyehouseForm) throws InternalException;

    List<ModelMap> findStatisticExportedFabrics(Long pageIndex, Long pageSize) throws InternalException;

    List<ModelMap> findDebtsWithPaging(Long pageIndex, Long pageSize) throws InternalException;

    ModelMap createDyehouse(CreateDyehouseForm createDyehouseForm) throws InternalException;
}
