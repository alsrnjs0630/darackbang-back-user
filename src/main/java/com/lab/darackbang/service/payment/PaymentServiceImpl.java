package com.lab.darackbang.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Value("${iamport.key}")
    private String restApiKey;

    @Value("${iamport.secret}")
    private String restApiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        try {
            this.iamportClient = new IamportClient(restApiKey, restApiSecret);
            log.info("IamportClient 초기화에 성공했습니다.");
        } catch (Exception e) {
            log.error("IamportClient 초기화에 실패했습니다: {} 에러: {}", e.getMessage(), e);
        }
    }

    @Override
    public IamportResponse<Payment> paymentByImpUid(String impUid) throws IamportResponseException, IOException {

        IamportResponse<Payment> paymentIamportResponse =iamportClient.paymentByImpUid(impUid);


        log.info("paymentIamportResponse getCode:{}", paymentIamportResponse.getCode());
        log.info("paymentIamportResponse getMessage:{}", paymentIamportResponse.getMessage());

        log.info("paymentIamportResponse getStatus:{}", paymentIamportResponse.getResponse().getStatus());

        return paymentIamportResponse;
    }
}