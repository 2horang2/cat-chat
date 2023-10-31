package com.horangya.catchat.login.repository;


import java.util.Optional;

import com.horangya.catchat.login.dto.OauthId;
import com.horangya.catchat.login.dto.OauthMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

    Optional<OauthMember> findByOauthId(OauthId oauthId);
}