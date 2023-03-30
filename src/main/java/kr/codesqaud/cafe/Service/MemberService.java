package kr.codesqaud.cafe.Service;

import kr.codesqaud.cafe.domain.Member;

import kr.codesqaud.cafe.memory.MemberRepository;
import kr.codesqaud.cafe.memory.MemoryMemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        memberRepository.save(member);
    }
}
