package com.pm.billing_service.controller;

import com.pm.billing_service.dto.BillingDTO;
import com.pm.billing_service.entity.Billing;
import com.pm.billing_service.grp.BillingGrpcService;
import com.pm.billing_service.mapper.BillingMapper;
import com.pm.billing_service.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/billing")
public class BillingServiceController {
    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceController.class);
    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<BillingDTO> createBilling(@RequestBody BillingDTO billingDTO) {
        Billing created = billingService.createPatientBilling(BillingMapper.toEntity(billingDTO));
        return ResponseEntity.ok(BillingMapper.toDTO(created));
    }
    @GetMapping
    public ResponseEntity<List<BillingDTO>> getAllBillingDetails(){
        List<BillingDTO> allBillingDetails= billingService.getAllBillingDetails();
        return ResponseEntity.ok().body(allBillingDetails);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BillingDTO> getBillingById(@PathVariable UUID id) {
        return billingService.getBillingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillingDTO> updateBilling(@PathVariable UUID id, @RequestBody BillingDTO billingDTO) {
        Billing entity = BillingMapper.toEntity(billingDTO);
        return billingService.updateBilling(id, entity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBilling(@PathVariable UUID id) {
        boolean deleted = billingService.deleteBilling(id);
        if (deleted) {
            return ResponseEntity.ok("Billing record deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
