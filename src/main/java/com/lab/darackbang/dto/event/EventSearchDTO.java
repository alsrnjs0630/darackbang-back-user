package com.lab.darackbang.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Schema(description = "이벤트 검색")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class EventSearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "이벤트 제목")
    private String title;

    @Schema(description = "이벤트 상태")
    private String eventState;
}
