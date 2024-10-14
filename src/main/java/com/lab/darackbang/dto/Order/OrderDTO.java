package com.lab.darackbang.dto.Order;

import com.lab.darackbang.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Schema(description = "주문 내역")
@Setter
@Getter
@ToString
public class OrderDTO {
    @NotNull
    @Size(max = 8)
    @Schema(description = "총 금액", requiredMode = Schema.RequiredMode.REQUIRED)
    private String totalOrderPrice;

    @NotNull
    @Schema(description = "주문일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate orderDate;

    @NotNull
    @Schema(description = "생성일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate createdDate;

    @NotNull
    @Schema(description = "수정일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate updatedDate;

}
