package org.inffy.domain.member.repository;

import org.inffy.domain.member.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
}
