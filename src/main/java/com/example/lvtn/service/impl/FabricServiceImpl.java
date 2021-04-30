package com.example.lvtn.service.impl;

import com.example.lvtn.dao.*;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.FabricDTO;
import com.example.lvtn.dto.StatisticCompletedFabric;
import com.example.lvtn.dto.StatisticRawFabric;
import com.example.lvtn.service.FabricService;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FabricServiceImpl implements FabricService {
    @Autowired
    FabricRepository fabricRepository;

    @Autowired
    FabricTypeRepository fabricTypeRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ImportSlipRepository importSlipRepository;

    @Autowired
    ExportSlipRepository exportSlipRepository;

    @Autowired
    DyehouseRepository dyehouseRepository;

    @Autowired
    DyeBatchRepository dyeBatchRepository;

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
    public List<FabricDTO> findFabricsByDyeBatchId(Long dyeBatchId) throws InternalException {
        try {
            List<Fabric> listFabric = fabricRepository.findCompletedFabricsByDyeBatchId(dyeBatchId);
            List<FabricDTO> listFabricDTO = new ArrayList<>();
            for (Fabric fabric: listFabric){
                listFabricDTO.add(FabricDTO.convertFabricToFabricDTO(fabric));
            }
            return listFabricDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public String getRawLength(Long fabricId) throws InternalException {
        try {
            Fabric fabric = fabricRepository.findFabricById(fabricId);
            return String.format("%.1f", fabric.getRawLength());
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public boolean isRawFabric(Long fabricId) throws InternalException {
        try {
            Fabric fabric = fabricRepository.findFabricById(fabricId);
            if (fabric.getStatus().equals(FabricStatus.IN_RAW)){
                return true;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<FabricDTO> findRawFabricDTOsByFabricType(String fabricType) throws InternalException {
        try {
            List<Fabric> listFabric = fabricRepository.findRawFabricsByFabricType(fabricType);
            List<FabricDTO> listFabricDTO = new ArrayList<>();
            for (Fabric fabric: listFabric){
                listFabricDTO.add(FabricDTO.convertFabricToFabricDTO(fabric));
            }
            return listFabricDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<FabricDTO> findExportedFabricDTOsByFabricType(Long dyehouseId, String fabricType) throws InternalException {
        try {
            List<Fabric> listFabric = new ArrayList<>();
            if (dyehouseId < 0L){
                listFabric = fabricRepository.findExportedFabricsByFabricType(fabricType);
            } else {
                listFabric = fabricRepository.findExportedFabricsInDyehouseByFabricType(dyehouseId, fabricType);
            }
            List<FabricDTO> listFabricDTO = new ArrayList<>();
            for (Fabric fabric: listFabric){
                listFabricDTO.add(FabricDTO.convertFabricToFabricDTO(fabric));
            }
            return listFabricDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public String createData() throws InternalException {
        try {
            Random random = new Random();
            List<Color> colors = colorRepository.findAll();
            List<ExportSlip> exportSlips = exportSlipRepository.findAll();

            for (int i = 0; i<500; i++){
                Double rawLength = 1000.0 * (random.nextInt(10) + 1);
                DyeBatch dyeBatch = dyeBatchRepository.findDyeBatchById((long) (random.nextInt(19) + 1));
                Color color = dyeBatch.getColor();
                Fabric newFabric = new Fabric(
                        rawLength,
                        rawLength - 100.0 * (random.nextInt(10) + 1),
                        FabricStatus.COMPLETED,
                        color.getName(),
                        color.getFabricType(),
                        color,
                        dyeBatch,
                        exportSlips.get(random.nextInt(17)),
                        dyeBatch.getDyehouse(),
                        new HashSet<Return>()
                );
                System.out.println(newFabric.toString());
                fabricRepository.save(newFabric);
            }
//            for (int i = 0; i<500; i++){
//                Color color = colors.get(random.nextInt(99));
//                Fabric newFabric = new Fabric(
//                        1000.0 * (random.nextInt(10) + 1),
//                        0.0,
//                        FabricStatus.EXPORTED,
//                        color.getName(),
//                        color.getFabricType(),
//                        null,
//                        null,
//                        exportSlips.get(random.nextInt(17)),
//                        dyehouseRepository.findDyehouseById((long) (random.nextInt(10) + 1)),
//                        new HashSet<Return>()
//                );
//                System.out.println(newFabric.toString());
//                fabricRepository.save(newFabric);
//            }
//            for (int i = 0; i<500; i++){
//                Color color = colors.get(random.nextInt(99));
//                Fabric newFabric = new Fabric(
//                        1000.0 * (random.nextInt(10) + 1),
//                        0.0,
//                        FabricStatus.IN_RAW,
//                        color.getName(),
//                        color.getFabricType(),
//                        color,
//                        null,
//                        null,
//                        null,
//                        new HashSet<Return>()
//                );
//                System.out.println(newFabric.toString());
//                fabricRepository.save(newFabric);
//            }


            return "created !!";
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<FabricDTO> findCompletedFabricDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Fabric> listFabric = new ArrayList<>();
            List<FabricDTO> listFabricDTO = new ArrayList<>();
            if (dyehouseId < 0L){
                listFabric = fabricRepository.findAllCompletedFabrics(pageIndex, pageSize);
            } else {
                listFabric = fabricRepository.findAllCompletedFabricsByDyehouseId(dyehouseId, pageIndex, pageSize);
            }
            for (Fabric fabric: listFabric){
                listFabricDTO.add(FabricDTO.convertFabricToFabricDTO(fabric));
            }
            return listFabricDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
