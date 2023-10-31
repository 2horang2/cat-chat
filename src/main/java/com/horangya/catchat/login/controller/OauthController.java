package com.horangya.catchat.login.controller;


import com.horangya.catchat.login.dto.OauthMember;
import com.horangya.catchat.login.dto.OauthServerType;
import com.horangya.catchat.login.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        log.error("check to login hyemim ");
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    // 추가
    @GetMapping("/login/{oauthServerType}")
    ResponseEntity<OauthMember> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam("code") String code
    ) {
        log.error("try to login hyemim " + code);
        OauthMember member = oauthService.login(oauthServerType, code);
        return ResponseEntity.ok(member);
    }

}