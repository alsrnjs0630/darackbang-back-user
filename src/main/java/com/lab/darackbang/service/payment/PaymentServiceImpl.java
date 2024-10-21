package com.lab.darackbang.service.payment;

import com.lab.darackbang.service.order.OrderService;
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
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;

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
    public IamportResponse<Payment> paymentByImpUid(String impUid, List<Long> cartItemIds) throws IamportResponseException, IOException {


        log.info("결제 아이디: ", impUid);

        IamportResponse<Payment> paymentIamportResponse =iamportClient.paymentByImpUid(impUid);

        log.info("paymentIamportResponse getCode:{}", paymentIamportResponse.getCode());
        log.info("paymentIamportResponse getMessage:{}", paymentIamportResponse.getMessage());

        log.info("paymentIamportResponse getStatus:{}", paymentIamportResponse.getResponse().getStatus());

        com.lab.darackbang.entity.Payment payment = new com.lab.darackbang.entity.Payment();

        payment.setApplyNum(paymentIamportResponse.getResponse().getApplyNum());

        //가상계좌 은행명
        payment.setBankName(paymentIamportResponse.getResponse().getBankName());

        //주문자 주소
        payment.setBuyerAddr(paymentIamportResponse.getResponse().getBuyerAddr());

        //주문자 이메일
        payment.setBuyerEmail(paymentIamportResponse.getResponse().getBuyerEmail());

        //주문자명
        payment.setBuyerName(paymentIamportResponse.getResponse().getBuyerName());

        //주문자 우편번호
        payment.setBuyerPostcode(paymentIamportResponse.getResponse().getBuyerPostcode());

        //주문자 연락처
        payment.setBuyerTel(paymentIamportResponse.getResponse().getBuyerTel());

        //카드명
        payment.setCardName(paymentIamportResponse.getResponse().getCardName());

        //카드 번호
        payment.setCardNumber(paymentIamportResponse.getResponse().getCardNumber());
        //할부 개월
        //기본값이 00
        payment.setCardQuota(Integer.toString(paymentIamportResponse.getResponse().getCardQuota()));

        //기본값이 00
        payment.setCurrency(paymentIamportResponse.getResponse().getCurrency());

        //가맹점 임의 지정 데이터
        payment.setCustomData(paymentIamportResponse.getResponse().getCustomData());

        //회원번호
        payment.setCustomerUid(paymentIamportResponse.getResponse().getCustomerUid());

        // IamPort 고유 결제 번호
        payment.setImpUid(paymentIamportResponse.getResponse().getImpUid());

        //주문번호
        payment.setMerchantUid(paymentIamportResponse.getResponse().getMerchantUid());

        //상품명
        payment.setName(paymentIamportResponse.getResponse().getName());

        //결제금액
        payment.setPaidAmount(paymentIamportResponse.getResponse().getAmount().intValue());

        //결제승인시각, 결제일
        // Unix timestamp 사용시  type, Mapper로 으로 변환
        payment.setPaidAt(paymentIamportResponse.getResponse().getPaidAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        //결제수단 구분코드
        payment.setPayMethod(paymentIamportResponse.getResponse().getPayMethod());

        //PG사 구분코드
        payment.setPgProvider(paymentIamportResponse.getResponse().getPgProvider());

        //PG사 거래번호
        payment.setPgTid(paymentIamportResponse.getResponse().getPgTid());

        // 결제 타입
        // 일반결제인 경우 무조건 payment로 전달
        payment.setPgType("payment");

        //거래 매출전표 URL
        payment.setReceiptUrl(paymentIamportResponse.getResponse().getReceiptUrl());

        //카드 유효기간
        //payment.setExpirationDate(paymentIamportResponse.getResponse().get);

        // 결제상태 (default 01 : 결제 대기, 02 : 결제 성공, 03 : 결제 실패)
        payment.setStatus(paymentIamportResponse.getResponse().getStatus());

        //결제 성공여부
        //결제승인 혹은 가상계좌 발급이 성공한 경우 true
        payment.setSuccess(paymentIamportResponse.getResponse().getImpUid() != null);

        // 결제 실패 원인
        payment.setFailReason(paymentIamportResponse.getResponse().getFailReason());

        payment.setOrder(orderService.registerOrder(cartItemIds));



    //결재 완료->저장 후 해당 cartItem들 삭제


        return paymentIamportResponse;
    }
}