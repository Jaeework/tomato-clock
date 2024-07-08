package com.tomatoclock.service;

import com.tomatoclock.domain.MemberVO;

public interface MemberService {

    public void createUser(MemberVO member);

    public MemberVO getUserById(String id);

}
