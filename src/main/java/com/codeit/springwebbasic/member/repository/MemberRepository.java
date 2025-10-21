package com.codeit.springwebbasic.member.repository;

import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemberRepository {
    private final Map<Long, Member> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    // 회원 저장
    public Member save(Member member) {
        if(member.getId() == null) {
            member.setId(sequence.getAndIncrement());
        }
        store.put(member.getId(), member);
        return member;
    }

    // id로 회원 조회
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // email로 회원 조회
    public Optional<Member> findByEmail(String email) {
        return store.values()
                .stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();

    }

    // email로 회원 존재 여부 확인
    public boolean existsByEmail(String email) {
        return store.values()
                .stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    // 전체 회원 조회
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 전달된 이름이 포함된 회원 조회
    public List<Member> findByNameContaining(String name) {
        return store.values()
                .stream()
                .filter(member -> member.getName().contains(name))
                .toList();
    }

    // 등급별 회원 조회
    public List<Member> findByGrade(MemberGrade grade) {
        return store.values()
                .stream()
                .filter(member -> member.getGrade() == grade)
                .toList();
    }

    // 회원 명수 조회
    public int count() {
        return store.size();
    }

    // 회원 전체 삭제
    public void clear() {
        store.clear();
    }
}
