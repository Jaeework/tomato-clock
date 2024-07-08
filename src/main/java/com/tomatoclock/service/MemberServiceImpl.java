package com.tomatoclock.service;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberMapper mapper;

    @Override
    public void createUser(MemberVO member) {
        mapper.insertUser(member);
    }

    @Override
    public MemberVO getUserById(String id) {
        return mapper.selectById(id);
    }
}
