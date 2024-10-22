package com.lab.darackbang.controller;

import com.lab.darackbang.entity.Order;
import com.lab.darackbang.service.payment.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 정보 확인 코드
     *
     * @param impUid
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    @PostMapping("/verifyIamport/{imp_uid}")
    public Map<String, String> paymentByImpUid(@PathVariable("imp_uid") String impUid, @RequestBody List<Long> cartItemIds) throws IamportResponseException, IOException {
        return paymentService.cartPayment(impUid, cartItemIds);
    }

    @PostMapping("/buynow/{imp_uid}/{productId}/{quantity}")
    public Map<String, String> paymentByImpUid(@PathVariable("imp_uid") String impUid, @PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) throws IamportResponseException, IOException {

        return paymentService.buyNowPayment(impUid, productId, quantity);
    }

}
