package uos.capstone.dms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uos.capstone.dms.domain.security.TokenDTO;
import uos.capstone.dms.domain.user.LoginRequestDTO;
import uos.capstone.dms.domain.user.MemberJoinRequestDTO;
import uos.capstone.dms.domain.user.MemberResponseDTO;
import uos.capstone.dms.security.SecurityUtil;
import uos.capstone.dms.service.MemberService;

@Tag(name = "사용자 관련", description = "사용자 관련 API")
@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> memberSignup(@ModelAttribute MemberJoinRequestDTO memberJoinRequestDTO) {

        memberJoinRequestDTO.setPassword(passwordEncoder.encode(memberJoinRequestDTO.getPassword()));
        memberService.signup(memberJoinRequestDTO);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> memberLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        TokenDTO tokenDTO = memberService.login(loginRequestDTO);
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", tokenDTO.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .maxAge(tokenDTO.getDuration())
                .path("/")
                .build();

        return ResponseEntity.ok().header("Set-Cookie", responseCookie.toString()).body(tokenDTO.getAccessToken());
    }

    @Operation(summary = "회원정보 호출")
    @GetMapping("/getMemberData")
    public ResponseEntity<MemberResponseDTO> authenticate() {
        return ResponseEntity.ok(memberService.findMemberByUserId(SecurityUtil.getCurrentUsername()));
    }
}
