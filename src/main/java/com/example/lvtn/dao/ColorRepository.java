package com.example.lvtn.dao;

import com.example.lvtn.dom.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query("select c from Color c")
    List<Color> findAll();
}
