package com.tomatoclock.controller;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import com.tomatoclock.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/api/users")
@Log4j2
@AllArgsConstructor
public class ProfileController {

    private MemberService memberService;

    @Setter(onMethod_ = { @Autowired})
    private MemberMapper memberMapper;

    @Setter(onMethod_ = { @Autowired })
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public String signup(MemberVO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberMapper.insertUser(member);

        return "redirect:/login";
    }

    @PutMapping(value = "/me/email", consumes = "application/json")
    public ResponseEntity<String> updateEmail(@RequestBody Map<String, String> params, Principal principal) {
        String email = params.get("email");
        String currentPassword = params.get("currentPassword");

        String userid = principal.getName();

        MemberVO memberToUpdate = new MemberVO();
        memberToUpdate.setId(userid);
        memberToUpdate.setEmail(email);

        try {
            memberService.updateUserProfile(memberToUpdate, currentPassword);
            return ResponseEntity.ok("Email updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> params, Principal principal) {
        String currentPassword = params.get("currentPassword");
        String newPassword = params.get("newPassword");
        String confirmPassword = params.get("confirmPassword");


        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("New password and confirmation password do not match.");
        }

        String userid = principal.getName();

        MemberVO memberToUpdate = new MemberVO();
        memberToUpdate.setId(userid);
        memberToUpdate.setPassword(newPassword);

        try {

            memberService.updateUserProfile(memberToUpdate, currentPassword);

            return ResponseEntity.ok("Password updated successfully.");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteAccount(@RequestBody Map<String, String> params, Principal principal) {
        String password = params.get("password");
        String userId = principal.getName();

        try {
            memberService.deactivateUser(userId, password);
            return ResponseEntity.ok("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
