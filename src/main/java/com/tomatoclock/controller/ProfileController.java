package com.tomatoclock.controller;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@Log4j2
@AllArgsConstructor
public class ProfileController {

    private MemberService memberService;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public void profile() {

    }

    @PostMapping("api/profile/update/email")
    public ResponseEntity<String> updateEmail(@RequestParam("email") String email,
                                              @RequestParam("currentPassword") String currentPassword, Principal principal) {

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

    @PostMapping("api/profile/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam("currentPassword") String currentPassword,
                                                @RequestParam("newPassword") String newPassword,
                                                @RequestParam("confirmPassword") String confirmPassword, Principal principal) {

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

}
