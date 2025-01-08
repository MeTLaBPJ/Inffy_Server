package org.inffy.domain.member.repository;

import org.inffy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findBySchoolEmail(String schoolEmail);

    boolean existsBySchoolEmail(String email);
    boolean existsByNickname(String nickname);
}
