package kr.codesqaud.cafe;

import kr.codesqaud.cafe.Service.MemberService;
import kr.codesqaud.cafe.memory.MemberRepository;
import kr.codesqaud.cafe.memory.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
