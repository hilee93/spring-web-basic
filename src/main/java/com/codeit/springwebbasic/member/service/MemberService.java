package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(MemberCreateRequestDto request) {
        // 이메일 중복 체크
//        Optional<Member> byEmail = memberRepository.findByEmail(request.getEmail());
//        if (byEmail.isPresent()) {
        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email이 이미 존재합니다.");
        }

        Member member = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .grade(MemberGrade.BRONZE)
                .joinedAt(LocalDateTime.now())
                .build();

        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    public List<Member> getMembers(String name) {
        return memberRepository.findAll();
    }

    public List<Member> searchMembers(String name) {
        return memberRepository.findByNameContaining(name);
    }
}
