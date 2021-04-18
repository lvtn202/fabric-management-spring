package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dao.FabricRepository;
import com.example.lvtn.dao.FabricTypeRepository;
import com.example.lvtn.dao.OrderRepository;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.FabricType;
import com.example.lvtn.dto.DyehouseDTO;
import com.example.lvtn.dto.StatisticRawFabric;
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
    private FabricTypeRepository fabricTypeRepository;

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
    public ModelMap updateDyehouse(UpdateDyehouseForm updateDyehouseForm) throws InternalException {
        try {
            Dyehouse currentDyehouse = dyehouseRepository.findDyehouseById(updateDyehouseForm.getId());
            currentDyehouse.setAddress(updateDyehouseForm.getAddress());
            currentDyehouse.setPhoneNumber(updateDyehouseForm.getPhoneNumber());
            currentDyehouse.setEmail(updateDyehouseForm.getEmail());
            Dyehouse updatedDyehouse = dyehouseRepository.save(currentDyehouse);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", updatedDyehouse.getId());
            modelMap.addAttribute("name", updatedDyehouse.getName());
            modelMap.addAttribute("address", updatedDyehouse.getAddress());
            modelMap.addAttribute("phoneNumber", updatedDyehouse.getPhoneNumber());
            modelMap.addAttribute("email", updatedDyehouse.getEmail());
            modelMap.addAttribute("debt", String.format("%.1f", updatedDyehouse.getDebt()));

            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<ModelMap> findStatisticExportedFabrics(Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Dyehouse> listDyehouse = dyehouseRepository.findAll();
            List<FabricType> listFabricType = fabricTypeRepository.findAll();
            List<ModelMap> listModelMap = new ArrayList<>();
            for (Dyehouse dyehouse: listDyehouse){
                ModelMap newModelMap = new ModelMap();
                newModelMap.addAttribute("dyehouseId", dyehouse.getId());
                newModelMap.addAttribute("dyehouseName", dyehouse.getName());

                List<StatisticRawFabric> fabricTypes = new ArrayList<>();
                List<Fabric> listExportedFabricInDyehouse = fabricRepository.findRawFabricsByDyehouseId(dyehouse.getId());
                for (FabricType fabricType: listFabricType){
                    StatisticRawFabric newStatisticRawFabric = new StatisticRawFabric(fabricType.getType(), 0L, "0.0");
                    for (Fabric fabric: listExportedFabricInDyehouse){
                        if(fabric.getFabricType().getType().equals(fabricType.getType())){
                            newStatisticRawFabric.setRawNumber(newStatisticRawFabric.getRawNumber() + 1);
                            newStatisticRawFabric.setRawLength(String.format("%.1f", Double.parseDouble(newStatisticRawFabric.getRawLength()) + fabric.getRawLength()));
                        }
                    }
                    fabricTypes.add(newStatisticRawFabric);
                }
                newModelMap.addAttribute("fabricTypes", fabricTypes);

                listModelMap.add(newModelMap);
            }

            int fromIndex = (int)(pageIndex * pageSize) > listModelMap.size() ? listModelMap.size() : (int)(pageIndex * pageSize);
            int toIndex = (int)(pageIndex * pageSize + pageSize) > listModelMap.size() ? listModelMap.size() : (int)(pageIndex * pageSize + pageSize);
            return listModelMap.subList(fromIndex,toIndex);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
