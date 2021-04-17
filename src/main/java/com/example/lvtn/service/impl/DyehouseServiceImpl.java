package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dao.FabricRepository;
import com.example.lvtn.dao.OrderRepository;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.DyehouseDTO;
import com.example.lvtn.dto.UpdateDyehouseForm;
import com.example.lvtn.service.DyehouseService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class DyehouseServiceImpl implements DyehouseService {
    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Autowired
    private FabricRepository fabricRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Dyehouse> findAll() {
        return dyehouseRepository.findAll();
    }

    @Override
    public List<DyehouseDTO> findDyehouseDTOsByNameWithPaging(String dyehouseName, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<DyehouseDTO> dyehouseDTOList = new ArrayList<DyehouseDTO>();
            List<Fabric> listExportedFabrics = fabricRepository.findExportedFabrics();

            if (dyehouseName.equals("")){
                List<Dyehouse> dyehouseList =  dyehouseRepository.findDyehousesWithPaging(pageIndex,pageSize);
                for(Dyehouse dyehouse: dyehouseList){
                    dyehouseDTOList.add(DyehouseDTO.convertDyehouseToDyehouseDTO(dyehouse));
                }
            } else {
                List<Dyehouse> dyehouseList = dyehouseRepository.findDyehousesByDyehouseNameWithPaging(dyehouseName, pageIndex, pageSize);
                for(Dyehouse dyehouse: dyehouseList){
                    dyehouseDTOList.add(DyehouseDTO.convertDyehouseToDyehouseDTO(dyehouse));
                }
            }
            for (Fabric exportedFabric: listExportedFabrics){
                for (DyehouseDTO dyehouseDTO: dyehouseDTOList){
                    if (exportedFabric.getDyehouse().getId().equals(dyehouseDTO.getId())){
                        dyehouseDTO.setInStock(String.valueOf(String.format("%.1f", Double.parseDouble(dyehouseDTO.getInStock()) + exportedFabric.getRawLength())));
                    }
                }
            }

            return dyehouseDTOList;
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap findDetailDyehouseById(Long id) throws InternalException {
        try {
            Dyehouse currentDyehouse = dyehouseRepository.findDyehouseById(id);
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", id);
            modelMap.addAttribute("name", currentDyehouse.getName());
            modelMap.addAttribute("address", currentDyehouse.getAddress());
            modelMap.addAttribute("phoneNumber", currentDyehouse.getPhoneNumber());
            modelMap.addAttribute("email", currentDyehouse.getEmail());
            modelMap.addAttribute("debt", String.format("%.1f", currentDyehouse.getDebt()));

            List<Fabric> listCurrentFabrics = fabricRepository.findCompletedFabricsByDyehouseId(currentDyehouse.getId());
            Double sumInStock = 0.0;
            for (Fabric fabric: listCurrentFabrics){
                sumInStock += fabric.getRawLength();
            }
            modelMap.addAttribute("inStock", String.format("%.1f", sumInStock));

            return modelMap;
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public Dyehouse updateDyehouse(UpdateDyehouseForm updateDyehouseForm) throws InternalException {
        try {
            Dyehouse currentDyehouse = dyehouseRepository.findDyehouseById(updateDyehouseForm.getId());
            currentDyehouse.setAddress(updateDyehouseForm.getAddress());
            currentDyehouse.setPhoneNumber(updateDyehouseForm.getPhoneNumber());
            currentDyehouse.setEmail(updateDyehouseForm.getEmail());
            return dyehouseRepository.save(currentDyehouse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
