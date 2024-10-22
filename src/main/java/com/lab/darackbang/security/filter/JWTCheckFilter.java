package com.lab.darackbang.security.filter;

import com.google.gson.Gson;
import com.lab.darackbang.common.utils.JWTUtil;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7); // "Bearer " 부분 제거

            log.info("여기는 오냐?");

            try {
                // JWT 검증 및 클레임 추출
                Map<String, Object> claims = JWTUtil.validateToken(token);

                // 권한 정보 추출 (List<Map<String, String>> 형식을 List<String>으로 변환)
                log.info("권한 이름 :{} ", claims.get("roleNames"));
                List<String> roles = (List<String>) claims.get("roleNames");
                /*List<String> roles = rolesMap.stream()
                        .map(roleMap -> roleMap.get("authority")) // "authority" 값을 추출
                        .collect(Collectors.toList());*/
                log.info("권한 이름 :{} ", claims.get("roleNames"));

                // 사용자 인증 객체 생성 및 건텍스트 설정
                LoginDTO userDetails = new LoginDTO((String) claims.get("email"), "", "", roles, (String) claims.get("memberState"));

                log.info("유저 디테일: {}", userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                log.info("인증 객체: {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // JWT 검증 실패 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(new Gson().toJson(Map.of("error", "ERROR_ACCESSDENIED")));
                log.error("Invalid Token Exception", e);  // 예외의 전체 스택트레이스를 로그로 출력
                return;
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 전달
    }
}
