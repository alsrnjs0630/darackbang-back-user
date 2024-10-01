package com.lab.darackbang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("api")
public class LoginController {
    @GetMapping("/member/logout")
    public Map<String,String> logout() {
        log.info("로그아웃 성공 -----------------------------");
        return Map.of("RESULT", "SUCCESS");
    }
}
