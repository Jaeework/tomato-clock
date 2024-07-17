package com.tomatoclock.controller;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class CommonController {

    @Setter(onMethod_ = { @Autowired })
    private MemberMapper memberMapper;

    @Setter(onMethod_ = { @Autowired })
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberVO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberMapper.insertUser(member);

        return "redirect:/login";
    }

}
