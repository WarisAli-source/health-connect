package com.pm.billing_service.grp;

import billing.BillingDeactivateRequest;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import com.pm.billing_service.entity.Billing;
import com.pm.billing_service.mapper.BillingMapper;
import com.pm.billing_service.repository.BillingRepository;
import com.pm.billing_service.service.BillingService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(
            BillingGrpcService.class);
    @Autowired
    private BillingService billingService;
    @Autowired
    private BillingRepository billingRepository;



    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {

        log.info("createBillingAccount request received {}", billingRequest.toString());
        Billing billing = BillingMapper.toModel(billingRequest);

        Billing savedBillingRequest = billingService.createPatientBilling(billing);

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(savedBillingRequest.getId().toString())
                .setStatus(savedBillingRequest.getStatus())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deactivateBillingAccount(BillingDeactivateRequest request,
                                         StreamObserver<BillingResponse> responseObserver) {
        log.info("Deactivate billing request received for patientId {}", request.getPatientId());

        Optional<Billing> billingOptional = billingRepository.findByPatientId(request.getPatientId());
        if (billingOptional.isPresent()) {
            Billing billing = billingOptional.get();
            billing.setStatus("INACTIVE");
            billingRepository.save(billing);

            BillingResponse response = BillingResponse.newBuilder()
                    .setAccountId(billing.getId().toString())
                    .setStatus(billing.getStatus())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription("Billing not found").asRuntimeException());
        }
    }
    @Override
    public void updateBillingAccount(BillingRequest request,
                                     StreamObserver<BillingResponse> responseObserver){
        log.info("update billing request received for patientId {}", request.getPatientId());

        Billing billing = billingRepository.findByPatientId(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Billing record not found for patientId " + request.getPatientId()));
        billing.setName(request.getName());
        billingRepository.save(billing);
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(billing.getId().toString())
                .setStatus(billing.getStatus())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
