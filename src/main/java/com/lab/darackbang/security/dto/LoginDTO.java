package com.lab.darackbang.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link org.springframework.security.core.userdetails.User} entity.
 */
@Schema(description = "로그인정보")
@Getter
@Setter
@ToString
public class LoginDTO extends User {

    private static final long serialVersionUID = 1L;

    // 관리자로그인ID
    @NotNull
    @Size(max = 120)
    @Schema(description = "로그인ID")
    private String userEmail;

    // 패스워드
    @NotNull
    @Size(max = 255)
    @Schema(description = "패스워드")
    private String password;

    // 회원이름
    @NotNull
    @Size(max = 15)
    @Schema(description = "회원이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    // 회원권한
    @NotNull
    @Schema(description = "회원롤정보", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> roleNames;

    // 회원상태
    @NotNull
    @Size(max = 2)
    @Schema(description = "회원상태", requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberState;

    public LoginDTO(String userEmail, String password, String name, List<String> roleNames, String memberState) {
        super(userEmail, password == null ? "" : password, roleNames.stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));

        this.userEmail = userEmail;
        this.password = password == null ? "" : password;
        this.name = name == null ? "" : name;
        this.roleNames = roleNames;
        this.memberState = memberState;
    }

    // JWT 토큰 PayLoad에 넣을 내용 ( 유저이메일, 이름, 권한 )
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", userEmail);
        dataMap.put("password", password);
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);
        dataMap.put("memberState", memberState);
        return dataMap;
    }

}
