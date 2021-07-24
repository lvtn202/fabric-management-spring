package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DebtRepository;
import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dom.*;
import com.example.lvtn.service.DebtService;
import com.example.lvtn.service.ImportSlipService;
import com.example.lvtn.service.PaymentService;
import com.example.lvtn.service.ReturnSlipService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ReturnSlipService returnSlipService;

    @Autowired
    private PaymentService paymentService;

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
    public Debt createDebt(Long type, Long idTransaction, Double amount, Timestamp createDate, Double oldTotal) throws InternalException {
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
            List<Debt> debts = new ArrayList<>();

            for (ImportSlip importSlip: importSlips){
                Dyehouse dyehouse = dyehouseRepository.findDyehouseById(importSlip.getOrder().getDyehouse().getId());
                Double oldDebt = dyehouse.getDebt() - importSlip.getMoney();
                debts.add(new Debt(1L, importSlip.getId(), importSlip.getMoney(), importSlip.getCreateDate(), oldDebt));
            }
            for (ReturnSlip returnSlip: returnSlips){
                Double oldDebt = returnSlip.getDyehouse().getDebt() + returnSlip.getMoney();
                debts.add(new Debt(2L, returnSlip.getId(), returnSlip.getMoney(), returnSlip.getReturnDate(), oldDebt));
            }
            for (Payment payment: payments){
                Double oldDebt = payment.getDyehouse().getDebt() + payment.getMoney();
                debts.add(new Debt(3L, payment.getId(), payment.getMoney(), payment.getCreateDate(), oldDebt));
            }

            Collections.sort(debts, new Comparator<Debt>() {
                @Override
                public int compare(Debt o1, Debt o2) {
                    return o1.getCreateDate().before(o2.getCreateDate()) ? -1
                            : o1.getCreateDate().after(o2.getCreateDate()) ? 1
                            : 0;
                }
            });
            for (Debt debt: debts){
                debtRepository.save(debt);
            }
            return "Created";

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
