package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.EmployeeRepositoryCustom;
import com.example.lvtn.dom.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    @Query("select e from Employee e order by e.id")
    List<Employee> findAll();
}
