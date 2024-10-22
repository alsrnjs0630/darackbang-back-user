package com.lab.darackbang.service.payment;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    Map<String, String> cartPayment(String impUid, List<Long> cartItemIds) throws IamportResponseException, IOException;
    Map<String, String> buyNowPayment(String impUid, Long productId, Integer quantity) throws IamportResponseException, IOException;
}