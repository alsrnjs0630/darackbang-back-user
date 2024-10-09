package com.lab.darackbang.security.handler;

import com.google.gson.Gson;
import com.lab.darackbang.common.utils.JWTUtil;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil = new JWTUtil();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        /* 세션 사용 시
        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal(); // 요청한 사용자 정보를 불러와서 LoginDTO 객체로 변환
        Map<String, Object> claims = loginDTO.getClaims();

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims); // 사용자 정보를 JSON 타입 문자열로 별환

        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonStr);
        printWriter.close();

        // 리디렉션 추가. 로그인 성공 후 전환시킬 페이지*/

        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();

        // LoginDTO에서 역할을 가져오고 "ROLE_" 접두사를 제거
        List<String> roleNamesWithoutPrefix = loginDTO.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // GrantedAuthority에서 역할 이름을 가져옴
                .map(role -> role.replace("ROLE_", "")) // "ROLE_" 접두사를 제거
                .collect(Collectors.toList()); // List<String>으로 변환

        // 사용자 정보 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", loginDTO.getUsername());
        claims.put("roleNames", roleNamesWithoutPrefix);

        // 10분짜리 JWT 생성
        String token = jwtUtil.generateToken(claims, 10);

        // JWT를 응답으로 전송
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("{\"token\":\"" + token + "\" , \"memberState\":\"" + loginDTO.getMemberState() + "\"}");
//        response.getWriter().print(jsonStr);

    }
}
