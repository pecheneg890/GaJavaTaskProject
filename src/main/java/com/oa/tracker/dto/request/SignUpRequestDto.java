package com.oa.tracker.dto.request;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    String email;
    String password;
    String firstName;
    String lastName;
}
