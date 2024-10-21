package com.lab.darackbang.service.payment;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    IamportResponse<Payment> paymentByImpUid(String impUid, List<Long> cartItemIds) throws IamportResponseException, IOException;
}