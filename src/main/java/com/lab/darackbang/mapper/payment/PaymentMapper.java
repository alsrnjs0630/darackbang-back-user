package com.lab.darackbang.mapper.payment;

import com.lab.darackbang.entity.Payment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(com.siot.IamportRestClient.response.Payment payment);
}
