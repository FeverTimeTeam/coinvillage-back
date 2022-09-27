package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.member.Authority;
import com.fevertime.coinvillage.domain.member.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);

    Member findByNickname(String nickname);

    Member findByEmail(String email);

    List<Member> findByNicknameContaining(String searchWord, Sort sort);

    List<Member> findByCountry_CountryNameAndNicknameContaining(String searchWord, Sort sort, String email);

    List<Member> findAllByCountry_CountryNameAndAuthoritiesIn(String email, Set<Authority> authorities, Sort sort);
}
