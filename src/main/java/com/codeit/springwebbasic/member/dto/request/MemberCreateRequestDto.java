package com.codeit.springwebbasic.member.dto.request;

import com.codeit.springwebbasic.member.entity.MemberGrade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequestDto {
    @NotBlank(message = "id 입력은 필수 입니다.")
    private Long id;

    @NotBlank(message = "이름 입력은 필수 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String name;

    @NotBlank(message = "email 입력은 필수 입니다.")
    private String email;

    @NotBlank(message = "전화번호 입력은 필수 입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$\n", message = "전화번호 형식이 알맞지 않습니다.")
    private String phone;

    @NotBlank(message = "등급이 잘못 설정되었습니다.")
    private MemberGrade grade;

    @PastOrPresent(message = "가입 날짜가 아닙니다.")
    private LocalDateTime joinedAt;
}
