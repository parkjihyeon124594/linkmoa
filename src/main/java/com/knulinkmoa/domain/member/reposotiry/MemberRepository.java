package com.knulinkmoa.domain.member.reposotiry;

import com.knulinkmoa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}