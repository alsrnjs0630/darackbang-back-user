package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Schema(description = "상품 검색")
public class ProductSearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "상품 이름")
    private String productName;

    @Schema(description = "상품 가격")
    private Integer salePrice;
}
