package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.MemberResponseDto;
import kr.codesqaud.cafe.dto.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.SignUpRequestDto;
import kr.codesqaud.cafe.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String signUp(SignUpRequestDto signUpRequestDto) {
        if (signUpRequestDto == null) {
            throw new IllegalArgumentException("signUpRequestDto가 null입니다.");
        }

        Member member = signUpRequestDto.toEntity();

        if (member == null) {
            throw new IllegalArgumentException("Member 객체를 생성할 수 없습니다.");
        }

        return memberRepository.save(member);
    }

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }

}
