package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Dyehouse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DyehouseRepositoryCustom {
    List<Dyehouse> findDyehousesByDyehouseNameWithPaging(String dyehouseName, Long pageIndex, Long pageSize);

    List<Dyehouse> findDyehousesWithPaging(Long pageIndex, Long pageSize);

    Dyehouse findDyehouseById(Long id);
}
