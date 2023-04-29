package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.member.MemberLoginRequestDto;
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.exception.common.CommonExceptionType;
import kr.codesqaud.cafe.exception.member.MemberExceptionType;
import kr.codesqaud.cafe.exception.member.MemberJoinException;
import kr.codesqaud.cafe.exception.member.MemberLoginException;
import kr.codesqaud.cafe.exception.member.MemberProfileEditException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.session.LoginMemberSession;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        Member member = memberJoinRequestDto.toUser();
        memberRepository.findByEmail(memberJoinRequestDto.getEmail()).ifPresent(m -> {
            throw new MemberJoinException(MemberExceptionType.DUPLICATED_EMAIL, memberJoinRequestDto);
        });
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> members = new ArrayList<>(memberRepository.findAll());
        members.sort(Comparator.comparing(Member::getCreateDate).reversed().thenComparing(Member::getMemberId));
        return members.stream().map(MemberResponseDto::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long memberId) {
        return MemberResponseDto.of(memberRepository.findById(memberId).orElseThrow(() -> new CommonException(CommonExceptionType.NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findByEmail(String email) {
        return MemberResponseDto.of(memberRepository.findByEmail(email).orElseThrow(() -> new CommonException(CommonExceptionType.NOT_FOUND)));
    }

    @Transactional
    public void update(ProfileEditRequestDto profileEditRequestDto) {
        Member findMember = memberRepository.findById(profileEditRequestDto.getMemberId()).orElseThrow(() -> new CommonException(CommonExceptionType.NOT_FOUND));

        if (findMember.isDifferentNickname(profileEditRequestDto.getNickname())) {
            memberRepository.findByNickname(profileEditRequestDto.getNickname())
                    .ifPresent(m -> {
                        throw new MemberProfileEditException(MemberExceptionType.DUPLICATED_MEMBER_NICKNAME, profileEditRequestDto);
                    });
        }

        findMember.setNickname(profileEditRequestDto.getNickname());
        memberRepository.update(findMember);
    }
    @Transactional
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
    @Transactional
    public LoginMemberSession login(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail()).orElseThrow(() -> new MemberLoginException(MemberExceptionType.NOT_FOUND, memberLoginRequestDto));
        if (member.isNotMatchedPassword(memberLoginRequestDto.getPassword())) {
            throw new MemberLoginException(MemberExceptionType.NOT_FOUND, memberLoginRequestDto);
        }
        return new LoginMemberSession(member.getEmail(),member.getMemberId());
    }
}
