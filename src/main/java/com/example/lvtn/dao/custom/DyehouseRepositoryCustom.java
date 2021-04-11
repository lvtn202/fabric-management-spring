package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;

import java.util.List;

public interface DyehouseRepositoryCustom {
    List<Dyehouse> findDyehousesByDyehouseNameWithPaging(String dyehouseName, Long pageIndex, Long pageSize);

    List<Dyehouse> findDyehousesWithPaging(Long pageIndex, Long pageSize);

    Dyehouse findDyehouseById(Long id);
}
