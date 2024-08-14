package com.tomatoclock.security;

import com.tomatoclock.domain.MemberVO;
import com.tomatoclock.mapper.MemberMapper;
import com.tomatoclock.security.domain.CustomUserDetails;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Setter(onMethod_ = { @Autowired })
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.warn("Load User By UserName : " + username);

        // userName means userid
        MemberVO vo = memberMapper.selectById(username);

        log.warn("queried by member mapper : " + vo);

        if(vo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // enabled 값이 '0'이면 계정이 비활성화되었음을 표시
        if (!vo.isEnabled()) {
            throw new UsernameNotFoundException("Account is disabled");
        }

        return new CustomUserDetails(vo);
    }

}
