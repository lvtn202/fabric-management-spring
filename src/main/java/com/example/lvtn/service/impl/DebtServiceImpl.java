package com.example.lvtn.service.impl;

import com.example.lvtn.dao.*;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.DebtDTO;
import com.example.lvtn.service.DebtService;
import com.example.lvtn.service.ImportSlipService;
import com.example.lvtn.service.PaymentService;
import com.example.lvtn.service.ReturnSlipService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DebtServiceImpl implements DebtService {
    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private ImportSlipService importSlipService;

    @Autowired
    private ImportSlipRepository importSlipRepository;

    @Autowired
    private ReturnSlipService returnSlipService;

    @Autowired
    private ReturnSlipRepository returnSlipRepository;

    @Autowired
    private ReturnRepository returnRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Override
    public List<Debt> findAll() throws InternalException {
        try {
            return debtRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Debt createDebt(Long type, Dyehouse dyehouse, Long idTransaction, Double amount, Timestamp createDate, Double oldTotal) throws InternalException {
        try {
            Double newTotal = 0.0;
            if(type.equals(1L)){
                newTotal = oldTotal + amount;
            } else if (type.equals(2L) || type.equals(3L)){
                newTotal = oldTotal - amount;
            } else {
                throw new InternalException("TYPE_ERROR");
            }

            Debt newDebt = new Debt(
              type,
              dyehouse,
              idTransaction,
              amount,
              createDate,
              newTotal
            );
            debtRepository.save(newDebt);
            return newDebt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String createData() throws InternalException {
        try {

            List<ImportSlip> importSlips = importSlipService.findAll();
            List<ReturnSlip> returnSlips = returnSlipService.findAll();
            List<Payment> payments = paymentService.findAll();

            for (ImportSlip importSlip: importSlips){
                Double price = 0.0;
                Long number = 0L;
                for (DyeBatch dyeBatch: importSlip.getDyeBatches()){
                    for (Fabric fabric: dyeBatch.getFabrics()){
                        price += fabric.getFinishedLength() * fabric.getColor().getPrice();
                    }
                    number += dyeBatch.getFabrics().size();
                }
                importSlip.setMoney(price);
                importSlip.setFabricNumber(number);
                importSlipRepository.save(importSlip);
            }
            for (ReturnSlip returnSlip: returnSlips){
                Double price = 0.0;
                for (Return aReturn: returnSlip.getReturns()){
                    aReturn.setMoney(aReturn.getReturnLength() * aReturn.getFabric().getColor().getPrice());
                    returnRepository.save(aReturn);
                    price += aReturn.getMoney();
                }
                returnSlip.setMoney(price);
                returnSlipRepository.save(returnSlip);
            }
            for (Payment payment: payments){
                if (payment.getMoney() > 10000000){
                    payment.setMoney(payment.getMoney()/10);
                }
                paymentRepository.save(payment);
            }


            List<Debt> debts1 = debtRepository.findAll();
            for (Debt debt: debts1){
                if(debt.getType().equals(1L)){
                    ImportSlip importSlip = importSlipRepository.findImportSlipById(debt.getIdTransaction());
                    debt.setAmount(importSlip.getMoney());
                    debt.setCreateDate(importSlip.getCreateDate());
                } else if(debt.getType().equals(2L)){
                    ReturnSlip returnSlip = returnSlipRepository.findReturnSlipById(debt.getIdTransaction());
                    debt.setAmount(returnSlip.getMoney());
                    debt.setCreateDate(returnSlip.getReturnDate());
                } else if(debt.getType().equals(3L)){
                    Payment payment = paymentRepository.findPaymentById(debt.getIdTransaction());
                    debt.setAmount(payment.getMoney());
                    debt.setCreateDate(payment.getCreateDate());
                }
                debtRepository.save(debt);
            }


            List<Dyehouse> dyehouses = dyehouseRepository.findAll();
            for (Dyehouse dyehouse: dyehouses){
                dyehouse.setDebt(0.0);
                dyehouseRepository.save(dyehouse);
            }

            List<Debt> debts = debtRepository.findAll();
            for (Debt debt: debts){
                Dyehouse dyehouse = dyehouseRepository.findDyehouseById(debt.getDyehouse().getId());
                if(debt.getType().equals(1L)){
                    dyehouse.setDebt(dyehouse.getDebt() + debt.getAmount());
                    debt.setTotal(dyehouse.getDebt());
                } else if(debt.getType().equals(2L) || debt.getType().equals(3L)){
                    dyehouse.setDebt(dyehouse.getDebt() - debt.getAmount());
                    debt.setTotal(dyehouse.getDebt());
                }
                debtRepository.save(debt);
                dyehouseRepository.save(dyehouse);
            }

//            List<ImportSlip> importSlips = importSlipService.findAll();
//            List<ReturnSlip> returnSlips = returnSlipService.findAll();
//            List<Payment> payments = paymentService.findAll();
//            List<Debt> debts = new ArrayList<>();
//
//            for (ImportSlip importSlip: importSlips){
//                Dyehouse dyehouse = dyehouseRepository.findDyehouseById(importSlip.getOrder().getDyehouse().getId());
//                Double oldDebt = dyehouse.getDebt() - importSlip.getMoney();
//                debts.add(new Debt(1L, dyehouse, importSlip.getId(), importSlip.getMoney(), importSlip.getCreateDate(), oldDebt));
//            }
//            for (ReturnSlip returnSlip: returnSlips){
//                Double oldDebt = returnSlip.getDyehouse().getDebt() + returnSlip.getMoney();
//                debts.add(new Debt(2L, returnSlip.getDyehouse(), returnSlip.getId(), returnSlip.getMoney(), returnSlip.getReturnDate(), oldDebt));
//            }
//            for (Payment payment: payments){
//                Double oldDebt = payment.getDyehouse().getDebt() + payment.getMoney();
//                debts.add(new Debt(3L, payment.getDyehouse(), payment.getId(), payment.getMoney(), payment.getCreateDate(), oldDebt));
//            }

//            Collections.sort(debts, new Comparator<Debt>() {
//                @Override
//                public int compare(Debt o1, Debt o2) {
//                    return o1.getCreateDate().before(o2.getCreateDate()) ? -1
//                            : o1.getCreateDate().after(o2.getCreateDate()) ? 1
//                            : 0;
//                }
//            });
//            List<Debt> debts = debtRepository.findAll();
//            for (Debt debt: debts){
//                if(debt.getType().equals(1L)){
//                    ImportSlip importSlip = importSlipRepository.findImportSlipById(debt.getIdTransaction());
//                    debt.setDyehouse(importSlip.getOrder().getDyehouse());
//                } else if(debt.getType().equals(2L)){
//                    ReturnSlip returnSlip = returnSlipRepository.findReturnSlipById(debt.getIdTransaction());
//                    debt.setDyehouse(returnSlip.getDyehouse());
//                } else if(debt.getType().equals(3L)){
//                    Payment payment = paymentRepository.findPaymentById(debt.getIdTransaction());
//                    debt.setDyehouse(payment.getDyehouse());
//                }
//                debtRepository.save(debt);
//            }
            return "Created";

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap getDebt(Long dyehouseId, Timestamp startDate, Timestamp endDate) throws InternalException {
        try {
            Dyehouse currentDyehouse = dyehouseRepository.findDyehouseById(dyehouseId);
            List<Debt> debts = debtRepository.findAll();
            List<Debt> oldDebts = debtRepository.findDebtsByDyehouseIdInRange(dyehouseId, new Timestamp(0), startDate);
            List<Debt> currentDebts = debtRepository.findDebtsByDyehouseIdInRange(dyehouseId, startDate, endDate);
            Double oldDebt = 0.0;
            Double newDebt = 0.0;

            if (oldDebts.size() > 0){
                oldDebt = oldDebts.get(oldDebts.size()-1).getTotal();
            }

            if(currentDebts.size() == 0){
                newDebt = oldDebt;
            } else if (currentDebts.size() > 0){
                newDebt = currentDebts.get(currentDebts.size()-1).getTotal();
            }

            List<DebtDTO> debtDTOs = new ArrayList<>();
            for (Debt debt: currentDebts){
                debtDTOs.add(DebtDTO.convertDebtToDebtDTO(debt));
            }

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("dyehouseName", currentDyehouse.getName());
            modelMap.addAttribute("oldDebt", String.format("%.1f", oldDebt));
            modelMap.addAttribute("newDebt", String.format("%.1f", newDebt));
            modelMap.addAttribute("transactions", debtDTOs);
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
