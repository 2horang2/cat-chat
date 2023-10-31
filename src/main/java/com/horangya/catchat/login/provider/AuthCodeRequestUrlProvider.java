package com.horangya.catchat.login.provider;


import com.horangya.catchat.login.dto.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}