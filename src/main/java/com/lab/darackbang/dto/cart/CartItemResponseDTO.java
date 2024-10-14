package com.lab.darackbang.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "장바구니아이템정보")
@Getter
@Setter
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {

    @NotNull
    @Schema(description = "인덱스번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotNull
    @Schema(description = "장바구니아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cart;

    @NotNull
    @Schema(description = "상품아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long product;

    @NotNull
    @Size(max = 4)
    @Schema(description = "상품 수량", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotNull
    @Size(max = 7)
    @Schema(description = "상품 가격", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer productPrice;

}
