package kr.codesqaud.cafe.Service;

import kr.codesqaud.cafe.domain.Member;

import kr.codesqaud.cafe.memory.MemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signUp(Member member) {
        memberRepository.save(member); // 여기서 uid가 부여됨.

        return member.getUid();
    }

    public Member findMemberByUid(Long uid) {
        return memberRepository.findMemberByUid(uid);
    }
}
