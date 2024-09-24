package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Schema(description = "상품 이미지")
@Getter
@Setter
@ToString
public class ProductImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "상품이미지아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotNull
    @Schema(description = "이미지파일명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productFileName;


}
