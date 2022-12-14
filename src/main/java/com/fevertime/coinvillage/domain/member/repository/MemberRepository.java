package com.fevertime.coinvillage.domain.member.repository;

import com.fevertime.coinvillage.domain.member.entity.Authority;
import com.fevertime.coinvillage.domain.member.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);

    Member findByNickname(String nickname);

    Member findByEmail(String email);

    List<Member> findByNicknameContaining(String searchWord, Sort sort);

    List<Member> findByCountry_CountryNameAndNicknameContainingAndAuthoritiesIn(String countryName, String searchWord, Set<Authority> authorities, Sort sort);

    List<Member> findAllByCountry_CountryNameAndAuthoritiesIn(String email, Set<Authority> authorities, Sort sort);
}
