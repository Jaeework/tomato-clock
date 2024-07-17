package com.tomatoclock.security;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import com.tomatoclock.security.domain.CustomUserDetails;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Setter(onMethod_ = { @Autowired })
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // userName means userid
        MemberVO vo = memberMapper.selectById(username);

        return vo == null ? null : new CustomUserDetails(vo);
    }
}
