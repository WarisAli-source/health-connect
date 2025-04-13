package com.pm.patient_service.grpc;


import billing.BillingDeactivateRequest;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.google.api.Billing;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort) {

        log.info("Connecting to Billing Service GRPC service at {}:{}",
                serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name,
                                                String email) {

        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId)
                .setName(name).setEmail(email).build();

        log.info("sending request from patient service via GRPC: {}", request);

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }
    public BillingResponse deactivateBillingAccount(String patientId) {
        BillingDeactivateRequest request = BillingDeactivateRequest.newBuilder()
                .setPatientId(patientId)
                .build();

        BillingResponse response = blockingStub.deactivateBillingAccount(request);
        log.info("Deactivated billing for patientId {}: {}", patientId, response);
        return response;
    }
    public BillingResponse updateBillingAccount(String patientId, String name,
                                                String email) {

        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId)
                .setName(name).setEmail(email).build();

        log.info("sending request from update patient service via GRPC: {}", request);

        BillingResponse response = blockingStub.updateBillingAccount(request);
        log.info("Received response from update billing service via GRPC: {}", response);
        return response;
    }
}
