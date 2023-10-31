package com.horangya.catchat.login.config;


import com.horangya.catchat.login.dto.OauthMember;
import com.horangya.catchat.login.dto.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code);
}