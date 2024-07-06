package com.tomatoclock.mapper;

import com.tomatoclock.domain.MemberVO;

public interface MemberMapper {

    void insertUser(MemberVO member);

    MemberVO selectById(String id);

    MemberVO selectByEmail(String email);

}
