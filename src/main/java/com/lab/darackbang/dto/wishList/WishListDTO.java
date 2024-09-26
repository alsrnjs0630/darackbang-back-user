package com.lab.darackbang.dto.wishList;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "관심상품")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class WishListDTO {

    /*
    * 관심상품 아이디
    */
    @NotNull
    @Schema(description = "관심상품아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /*
    *  회원정보(회원아이디)
    */

    /*
    *  상품정보(상품아이디)
    * */
}
