package com.lab.darackbang.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "이벤트 목록 응답")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EventResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 이벤트 아이디 (인덱스 번호)
     * */
    @NotNull
    @Schema(description = "인덱스번호", required = true)
    private Long id;

    /*
     * 제목
     * */
    @NotNull
    @Size(max = 50)
    @Schema(description = "제목", required = true)
    private String title;

    /*
     * 이벤트 상태
     * */
    @NotNull
    @Size(max = 2)
    @Schema(description = "이벤트상태", required = true)
    private String eventState;

    /*
     * 이벤트 시작일
     * */
    @Schema(description = "이벤트시작일")
    private LocalDate startDate;

    /*
     * 이벤트 종료일
     * */
    @Schema(description = "이벤트종료일")
    private LocalDate endDate;

}
