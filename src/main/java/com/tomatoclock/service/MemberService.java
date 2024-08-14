package com.tomatoclock.service;

import com.tomatoclock.domain.MemberVO;

public interface MemberService {

    public void createUser(MemberVO member);

    public MemberVO getUserById(String id);

    void updateUserProfile(MemberVO member, String currentPassword);

    void deactivateUser(String userid, String password);
}
