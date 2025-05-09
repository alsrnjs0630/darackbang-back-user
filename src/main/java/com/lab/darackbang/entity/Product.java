package com.lab.darackbang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tbl_product")
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //상품아이디
    //@Id: 기본키
    //@GeneratedValue: 시퀀스 생성
    //@Column(name: 칼럼명, nullable: Null 허용 여부, length: 데이터 타입의 길이)
    //: 엔티티 클래스의 필드를 데이터베이스 테이블의 열(column)과 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //상품번호
    @Column(name = "pno", nullable = false, length = 12)
    private String pno;

    //상품명
    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    //상품설명
    @Column(name = "product_detail", nullable = true, length = 1000)
    private String productDetail;

    //소비자가
    @Column(name = "retail_price", nullable = false, length = 7)
    private Integer retailPrice;

    //판매가
    @Column(name = "sale_price", nullable = false, length = 7)
    private Integer salePrice;

    //유형
    @Column(name = "type", nullable = true, length = 10)
    private String type;

    //주의사항
    @Column(name = "caution", nullable = true, length = 1000)
    private String caution;

    //노출유무
    //@Builder.Default: 디폴트값 설정
    @Column(name = "is_visible", nullable = false, length = 1)
    @ColumnDefault("1")
    private Boolean isVisible;

    //제조사
    @Column(name = "manufacture", nullable = false, length = 50)
    private String manufacture;

    //브랜드
    @Column(name = "brand", nullable = true, length = 50)
    private String brand;

    //원산지
    @Column(name = "origin", nullable = false, length = 20)
    private String origin;

    //원료명
    @Column(name = "material", nullable = false, length = 50)
    private String material;

    //카테고리
    @Column(name = "category", nullable = false, length = 4)
    private String category;

    //판매회사명
    @Column(name = "sale_company", nullable = false, length = 50)
    private String saleCompany;

    //판매회사정보
    @Column(name = "sale_company_info", nullable = true, length = 1000)
    private String saleCompanyInfo;

    //삭제유무
    @Column(name = "is_deleted", nullable = false, length = 1)
    @ColumnDefault("0")
    private Boolean isDeleted;

    //품절유무
    @Column(name = "is_soldout", nullable = false, length = 1)
    @ColumnDefault("0")
    private Boolean isSoldout;

    //영양성분
    @Column(name = "nutrition", nullable = true, length = 1000)
    private String nutrition;

    //상품수량
    @Column(name = "quantity", nullable = false, length = 7)
    private Integer quantity;

    //상품포장수량
    @Column(name = "package_quantity", nullable = true, length = 7)
    private Integer packageQuantity;

    //소비기한
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    //제조년월일
    @Column(name = "manufacture_date", nullable = false)
    private LocalDate manufactureDate;

    //유전자변형유무
    @Column(name = "is_gmo", nullable = false, length = 1)
    @ColumnDefault("0")
    private Boolean isGmo;

    //용량
    @Column(name = "volume", nullable = true, length = 3)
    private Integer volume;

    //관심상품카운트
    @Column(name = "wish_count", nullable = false, length = 7)
    @ColumnDefault("0")
    private Integer wishCount;

    //productImages 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnoreProperties("product")  // 순환 참조 방지
    private List<ProductImage> productImages;

    // QandA 테이블 (qanda) 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    @ToString.Exclude
    private List<Qanda> qandas;

    // 구매후기 테이블 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    @ToString.Exclude
    private List<ProductReview> productReviews;

    // 구독 테이블 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    @ToString.Exclude
    private List<Subscribe> subscribes;

    // 관심상품 테이블 매핑 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    @ToString.Exclude
    private List<WishList> wishLists;
}
