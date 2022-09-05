package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);

    Member findByNickname(String nickname);

    Member findByEmail(String email);

    List<Member> findByNicknameContaining(String searchWord);
}
