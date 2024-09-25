package com.lab.darackbang.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab.darackbang.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lab.darackbang.entity.MemberRole} entity.
 */
@Schema(description = "회원롤정보")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class MemberRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 인덱스번호
     */
    @NotNull
    @JsonIgnore
    @Schema(description = "인덱스번호",requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 사용자 롤
     */
    @NotNull
    @Schema(description = "회원롤",requiredMode = Schema.RequiredMode.REQUIRED)
    private Role role;

    /**
     * 사용자 정보
    @NotNull
    @Schema(description = "회원정보",requiredMode = Schema.RequiredMode.REQUIRED)
    private MemberDTO member;*/
}
