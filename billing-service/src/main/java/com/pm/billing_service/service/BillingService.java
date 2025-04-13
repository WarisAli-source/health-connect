package com.pm.billing_service.service;

import com.pm.billing_service.dto.BillingDTO;
import com.pm.billing_service.entity.Billing;
import com.pm.billing_service.mapper.BillingMapper;
import com.pm.billing_service.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public Billing createPatientBilling(Billing billing){
        return billingRepository.save(billing);
    }
    public List<BillingDTO> getAllBillingDetails(){
        List<Billing> billing= billingRepository.findAll();
        return billing.stream().map(BillingMapper::toDTO).toList();
    }
    public Optional<BillingDTO> getBillingById(UUID id) {
        return billingRepository.findById(id)
                .map(BillingMapper::toDTO);
    }

    public Optional<BillingDTO> updateBilling(UUID id, Billing updatedBilling) {
        return billingRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedBilling.getName());
                    existing.setStatus(updatedBilling.getStatus());
                    Billing saved = billingRepository.save(existing);
                    return BillingMapper.toDTO(saved);
                });
    }

    public boolean deleteBilling(UUID id) {
        return billingRepository.findById(id)
                .map(billing -> {
                    billingRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
