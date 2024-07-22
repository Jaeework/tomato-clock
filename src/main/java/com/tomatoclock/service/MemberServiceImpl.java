package com.tomatoclock.service;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(MemberVO member) {
        memberMapper.insertUser(member);
    }

    @Override
    public MemberVO getUserById(String id) {
        return memberMapper.selectById(id);
    }

    @Override
    public void updateUserProfile(MemberVO memberToUpdate, String currentPassword) throws IllegalArgumentException {
        MemberVO currentUser = memberMapper.selectById(memberToUpdate.getId());

        // 현재 비밀번호가 일치하는지 확인
        if (currentUser != null && passwordEncoder.matches(currentPassword, currentUser.getPassword())) {

            // 비밀번호 변경
            if (memberToUpdate.getPassword() != null) {

                // 새 비밀번호가 현재 비밀번호와 동일한지 확인
                if(currentPassword.equals(memberToUpdate.getPassword())) {
                    throw new IllegalArgumentException("You cannot use the same password as the current password.");
                }

                // 인코딩 작업
                String encodedPassword = passwordEncoder.encode(memberToUpdate.getPassword());
                memberToUpdate.setPassword(encodedPassword);
            }


            // 이메일 변경
            if(memberToUpdate.getEmail() != null) {
                String newEmail = memberToUpdate.getEmail();

                // 현재 이메일과 동일한지 확인
                if (currentUser.getEmail().equals(newEmail)) {
                    throw new IllegalArgumentException("You cannot use the same email as the current email.");
                }

                // 이미 사용 중인 이메일인지 확인
                if (memberMapper.selectByEmail(newEmail) != null) {
                    throw new IllegalArgumentException("The email address is already in use.");
                }

            }

            memberMapper.updateUserProfile(memberToUpdate);
        }

    }
}
