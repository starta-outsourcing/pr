package org.example.taskflowd.domain.teammember.repository;

import org.example.taskflowd.domain.teammember.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamMember,Long> {
}
