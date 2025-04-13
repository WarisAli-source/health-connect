package com.pm.billing_service.mapper;
import billing.BillingRequest;
import com.pm.billing_service.dto.BillingDTO;
import com.pm.billing_service.entity.Billing;

public class BillingMapper {

    // From gRPC Request to Entity
    public static Billing toModel(BillingRequest request) {
        Billing billing = new Billing();
        billing.setPatientId(request.getPatientId());
        billing.setName(request.getName());
        billing.setEmail(request.getEmail());
        billing.setStatus("ACTIVE"); // default when created
        return billing;
    }

    // From Entity to DTO
    public static BillingDTO toDTO(Billing billing) {
        BillingDTO dto = new BillingDTO();
        dto.setId(billing.getId());
        dto.setPatientId(billing.getPatientId());
        dto.setName(billing.getName());
        dto.setEmail(billing.getEmail());
        dto.setStatus(billing.getStatus());
        return dto;
    }

    // From DTO to Entity
    public static Billing toEntity(BillingDTO dto) {
        Billing billing = new Billing();
        billing.setId(dto.getId());
        billing.setPatientId(dto.getPatientId());
        billing.setName(dto.getName());
        billing.setEmail(dto.getEmail());
        billing.setStatus(dto.getStatus());
        return billing;
    }
}

