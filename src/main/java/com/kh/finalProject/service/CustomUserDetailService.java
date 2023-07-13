package com.kh.finalProject.service;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByMemberId(username);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return createMemberUserDetails(member);
        }

        Optional<com.kh.finalProject.entity.User> optionalUser = userRepository.findByUserId(username);
        if (optionalUser.isPresent()) {
            com.kh.finalProject.entity.User user = optionalUser.get();
            return createUserUserDetails(user);
        }

        throw new UsernameNotFoundException(username + "을 DB에서 찾을 수 없습니다.");
    }

    private UserDetails createMemberUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(
                String.valueOf(member.getMemberId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    private UserDetails createUserUserDetails(com.kh.finalProject.entity.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new User(
                String.valueOf(user.getUserId()),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}

