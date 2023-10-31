package com.horangya.catchat.login.service;

import com.horangya.catchat.login.config.AuthCodeRequestUrlProviderComposite;
import com.horangya.catchat.login.config.OauthMemberClientComposite;
import com.horangya.catchat.login.dto.OauthMember;
import com.horangya.catchat.login.dto.OauthServerType;
import com.horangya.catchat.login.repository.OauthMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    // 추가
    public OauthMember login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return saved;
    }
}

