package me.jaeseong.springboot_developer.springbootdeveloper;

import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
public class TestService {
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
