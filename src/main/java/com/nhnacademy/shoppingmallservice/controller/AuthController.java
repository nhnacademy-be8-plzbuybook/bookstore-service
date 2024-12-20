package com.nhnacademy.shoppingmallservice.controller;

import com.nhnacademy.shoppingmallservice.dto.LoginRequestDto;
import com.nhnacademy.shoppingmallservice.dto.MemberDto;
import com.nhnacademy.shoppingmallservice.service.CustomTokenService;
import com.nhnacademy.shoppingmallservice.service.MemberAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final CustomTokenService tokenService;
    private final MemberAuthService memberAuthService;

    @GetMapping("/")
    public String hi() {
        return "hi";
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
//        Optional<MemberDto> optionalMemberDto = memberAuthService.getMemberByEmail(loginRequest.email())
        MemberDto authenticatedMemberDto = memberAuthService.authenticate(loginRequest);
        //TODO: 임시로 문자열 반환해서 액세스토큰 프론트에 전달 -> json 형식으로 보내주게 바꿔야됨
        String accessToken = tokenService.issueJwt(response, authenticatedMemberDto);
        return ResponseEntity.status(HttpStatus.OK).body(accessToken);
    }
}