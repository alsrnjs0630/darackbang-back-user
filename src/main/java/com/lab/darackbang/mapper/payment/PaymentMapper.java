package com.lab.darackbang.mapper.payment;

import com.lab.darackbang.entity.Payment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(com.siot.IamportRestClient.response.Payment payment);

    @AfterMapping
    default void updatePayment(com.siot.IamportRestClient.response.Payment source, @MappingTarget Payment target) {
        target.setPaidAmount(source.getAmount().intValue());
        /*target.setPaidAt(source.getPaidAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .plusHours(7)
                .toLocalDateTime());*/
        target.setSuccess(true);
    }

}
