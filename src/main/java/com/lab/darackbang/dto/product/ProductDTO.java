package com.lab.darackbang.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "상품정보")
@Getter
@Setter
@ToString
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 상품아이디
    @NotNull
    @Schema(description = "상품아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    // 상품 번호
    @NotNull
    @Size(max = 12)
    @Schema(description = "상품번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pno;

    // 상품명
    @NotNull
    @Size(max = 50)
    @Schema(description = "상품명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productName;

    // 상품 설명
    @Null
    @Size(min = 2, max = 1000)
    @Schema(description = "상품설명")
    private String productDetail;

    // 소비자가
    @NotNull
    @Size(max = 7)
    @Schema(description = "소비자가", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer retailPrice;

    // 판매가
    @NotNull
    @Size(max = 7)
    @Schema(description = "판매가", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer salePrice;

    // 유형
    @Schema(description = "유형")
    private String type;

    // 주의사항
    @Size(max = 1000)
    @Schema(description = "주의사항")
    private String caution;

    // 노출유무 (디폴트값 : 1)
    @NotNull
    @Schema(description = "노출유무", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isVisible;

    // 제조사
    @NotNull
    @Size(max = 50)
    @Schema(description = "제조사", requiredMode = Schema.RequiredMode.REQUIRED)
    private String manufacture;

    // 브랜드
    @Size(max = 50)
    @Schema(description = "브랜드")
    private String brand;

    // 원산지
    @NotNull
    @Size(max = 20)
    @Schema(description = "원산지", requiredMode = Schema.RequiredMode.REQUIRED)
    private String origin;

    // 원료명
    @NotNull
    @Size(max = 50)
    @Schema(description = "원료명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String material;

    // 카테고리
    @NotNull
    @Size(max = 4)
    @Schema(description = "카테고리", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    // 판매회사명
    @NotNull
    @Size(max = 50)
    @Schema(description = "판매회사명", requiredMode = Schema.RequiredMode.REQUIRED)
    private String saleCompany;

    // 판매회사정보
    @Size(max = 1000)
    @Schema(description = "판매회사정보")
    private String saleCompanyInfo;

    // 삭제유무
    @NotNull
    @Schema(description = "삭제유무", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isDeleted;

    // 품절유무
    @NotNull
    @Schema(description = "품절유무", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSoldout;

    // 영양성분
    @Size(max = 1000)
    @Schema(description = "영양성분")
    private String nutrition;

    // 상품수량
    @NotNull
    @Size(max = 7)
    @Schema(description = "상품수량", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    // 상품포장수량
    @Size(max = 7)
    @Schema(description = "상품포장수량")
    private Integer packageQuantity;

    // 소비기한
    @NotNull
    @Schema(description = "소비기한", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate expirationDate;

    // 제조년월일
    @NotNull
    @Schema(description = "제조년월일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate manufactureDate;

    // 유전자변형유무
    @NotNull
    @Schema(description = "유전자변형유무", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isGmo;

    // 용량
    @Size(max = 3)
    @Schema(description = "용량")
    private Integer volume;

    // 관심상품카운트
    @NotNull
    @Size(max = 7)
    @Schema(description = "관심상품카운트", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer wishCount;

    // 상품 이미지 리스트
    @Schema(description = "상품 이미지 리스트")
    private List<ProductImageDTO> productImages;

    // 등록일
    @NotNull
    @Schema(description = "등록일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate createdDate;

    // 수정일
    @NotNull
    @Schema(description = "수정일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate updatedDate;

}
