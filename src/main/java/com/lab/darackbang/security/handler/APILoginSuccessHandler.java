package com.lab.darackbang.security.handler;

import com.google.gson.Gson;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal(); // 요청한 사용자 정보를 불러와서 LoginDTO 객체로 변환
        Map<String, Object> claims = loginDTO.getClaims();

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims); // 사용자 정보를 JSON 타입 문자열로 별환

        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonStr);
        printWriter.close();

        // 리디렉션 추가. 로그인 성공 후 전환시킬 페이지
    }
}
