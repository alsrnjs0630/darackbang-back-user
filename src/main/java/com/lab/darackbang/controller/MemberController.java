package com.lab.darackbang.controller;

import com.lab.darackbang.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api")
public class MemberController {

    @PostMapping("/login")
    public String join(Member member) {
        log.info("Member joined: " + member);

        return "redirect:/";
    }

}
