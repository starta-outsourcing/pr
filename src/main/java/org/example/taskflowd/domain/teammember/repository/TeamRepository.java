package org.example.taskflowd.domain.teammember.repository;

import org.example.taskflowd.domain.team.entity.Team;
import org.example.taskflowd.domain.teammember.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findByTeam(Team team);
    List<TeamMember> findByUserId(Long userId);
}
