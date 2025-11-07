package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto createMember(MemberCreateRequestDto request) {
        // 이메일 중복 체크
//        Optional<Member> byEmail = memberRepository.findByEmail(request.getEmail());
//        if (byEmail.isPresent()) {
        if(memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email이 이미 존재합니다.");
        }

        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .grade(MemberGrade.BRONZE)
                .joinedAt(LocalDateTime.now())
                .build();

        Member saved = memberRepository.save(member);
        return MemberResponseDto.from(member);
    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        log.info("findMember: {}", member);
        return MemberResponseDto.from(member);
    }

    public List<MemberResponseDto> getAllMembers() {

        List<Member> all = memberRepository.findAll();

        return getMemberResponseDtos(all);
    }


    public List<MemberResponseDto> searchMembers(String name) {

        List<Member> all = memberRepository.findByNameContaining(name);

        return getMemberResponseDtos(all);
    }

    private List<MemberResponseDto> getMemberResponseDtos(List<Member> all) {
        return all.stream()
                .map(member -> MemberResponseDto.from(member))
                .toList();
    }
}
