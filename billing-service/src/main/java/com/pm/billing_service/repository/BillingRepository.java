package com.pm.billing_service.repository;

import com.pm.billing_service.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillingRepository extends JpaRepository<Billing,UUID> {
    public Optional<Billing> findByPatientId(String id);
}
