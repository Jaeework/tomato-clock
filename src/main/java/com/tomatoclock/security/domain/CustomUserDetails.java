package com.tomatoclock.security.domain;

import com.tomatoclock.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private MemberVO member;

    public CustomUserDetails(MemberVO member) {
        this.member = member;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 권한 설정을 추가할 경우 변경
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return member.getEnabled() == 1 ? true : false;
    }


}
