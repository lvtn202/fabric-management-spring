package com.example.lvtn.service.impl;

import com.example.lvtn.dao.FabricRepository;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dto.StatisticCompletedFabric;
import com.example.lvtn.dto.StatisticExportedFabric;
import com.example.lvtn.dto.StatisticRawFabric;
import com.example.lvtn.service.FabricService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricServiceImpl implements FabricService {
    @Autowired
    FabricRepository fabricRepository;

    @Override
    public List<Fabric> findAll() {
        return fabricRepository.findAll();
    }

    @Override
    public List<StatisticRawFabric> findStatisticRawFabrics(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Fabric> listFabric = new ArrayList<>();
            if (dyehouseId < 0){
                listFabric = fabricRepository.findRawFabrics();
            } else {
                listFabric = fabricRepository.findRawFabricsByDyehouseId(dyehouseId);
            }

            List<StatisticRawFabric> listStatisticRawFabric = new ArrayList<StatisticRawFabric>();
            for (Fabric fabric: listFabric){
                boolean isExisted = false;
                for (StatisticRawFabric statisticRawFabric: listStatisticRawFabric){
                    if(statisticRawFabric.getFabricType().equals(fabric.getFabricType().getType())){
                        isExisted = true;
                        statisticRawFabric.setRawNumber(statisticRawFabric.getRawNumber() + 1);
                        statisticRawFabric.setRawLength(String.valueOf(Double.parseDouble(statisticRawFabric.getRawLength()) + fabric.getRawLength()));
                        break;
                    }
                }
                if (!isExisted){
                    listStatisticRawFabric.add(new StatisticRawFabric(
                            fabric.getFabricType().getType(),
                            1L,
                            String.valueOf(fabric.getRawLength())
                    ));
                }
            }

            int fromIndex = (int)(pageIndex * pageSize) > listStatisticRawFabric.size() ? listStatisticRawFabric.size() : (int)(pageIndex * pageSize);
            int toIndex = (int)(pageIndex * pageSize + pageSize) > listStatisticRawFabric.size() ? listStatisticRawFabric.size() : (int)(pageIndex * pageSize + pageSize);
            return listStatisticRawFabric.subList(fromIndex,toIndex);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<StatisticCompletedFabric> findStatisticCompletedFabricsInDyehouse(Long dyehouseId, Double startDate, Double endDate, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Fabric> listFabric = fabricRepository.findCompletedFabricsByDyehouseId(dyehouseId);
            listFabric.stream()
                    .filter(fabric -> fabric.getFinishedLength() > startDate && fabric.getFinishedLength() < endDate)
                    .collect(Collectors.toList());

            List<StatisticCompletedFabric> listStatisticCompletedFabric = new ArrayList<StatisticCompletedFabric>();
            for (Fabric fabric: listFabric){
                boolean isExisted = false;
                for (StatisticCompletedFabric statisticCompletedFabric: listStatisticCompletedFabric){
                    if(statisticCompletedFabric.getFabricType().equals(fabric.getFabricType().getType())){
                        isExisted = true;
                        statisticCompletedFabric.setDoneNumber(statisticCompletedFabric.getDoneNumber() + 1);
                        statisticCompletedFabric.setDoneLength(String.valueOf(Double.parseDouble(statisticCompletedFabric.getDoneLength()) + fabric.getFinishedLength()));
                        break;
                    }
                }
                if (!isExisted){
                    listStatisticCompletedFabric.add(new StatisticCompletedFabric(
                            fabric.getFabricType().getType(),
                            1L,
                            String.valueOf(fabric.getFinishedLength())
                    ));
                }
            }

            int fromIndex = (int)(pageIndex * pageSize) > listStatisticCompletedFabric.size() ? listStatisticCompletedFabric.size() : (int)(pageIndex * pageSize);
            int toIndex = (int)(pageIndex * pageSize + pageSize) > listStatisticCompletedFabric.size() ? listStatisticCompletedFabric.size() : (int)(pageIndex * pageSize + pageSize);
            return listStatisticCompletedFabric.subList(fromIndex,toIndex);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<StatisticExportedFabric> findStatisticExportedFabrics(Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Fabric> listFabric = fabricRepository.findExportedFabrics();

            List<StatisticExportedFabric> listStatisticExportedFabric = new ArrayList<StatisticExportedFabric>();
            for (Fabric fabric: listFabric){
                boolean isExisted = false;
                for (StatisticExportedFabric statisticExportedFabric: listStatisticExportedFabric){
                    if(statisticExportedFabric.getDyehouseName().equals(fabric.getDyehouse().getName())){
                        isExisted = true;
                        statisticExportedFabric.setRawNumber(statisticExportedFabric.getRawNumber() + 1);
                        statisticExportedFabric.setRawLength(String.valueOf(Double.parseDouble(statisticExportedFabric.getRawLength()) + fabric.getRawLength()));
                        break;
                    }
                }
                if (!isExisted){
                    listStatisticExportedFabric.add(new StatisticExportedFabric(
                            fabric.getDyehouse().getName(),
                            1L,
                            String.valueOf(fabric.getRawLength())
                    ));
                }
            }

            int fromIndex = (int)(pageIndex * pageSize) > listStatisticExportedFabric.size() ? listStatisticExportedFabric.size() : (int)(pageIndex * pageSize);
            int toIndex = (int)(pageIndex * pageSize + pageSize) > listStatisticExportedFabric.size() ? listStatisticExportedFabric.size() : (int)(pageIndex * pageSize + pageSize);
            return listStatisticExportedFabric.subList(fromIndex,toIndex);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
