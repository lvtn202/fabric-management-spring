package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Color;

import java.util.List;

public interface ColorRepositoryCustom {
    List<Color> findColorsByFabricTypeId(Long fabricTypeId);
}
