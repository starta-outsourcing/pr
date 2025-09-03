package org.example.taskflowd.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflowd.common.entity.BaseEntity;
import org.example.taskflowd.domain.teammember.entity.TeamMember;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID idPk;

    private String name;

    private String description;

    private String members;

    // 양방향 관계 설정
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team(String name, String description) {
        this.idPk = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    // 비즈니스 메서드
    public void updateTeamInfo(String name, String description) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    public void updateMembers(String members) {
        this.members = members;
    }

    // 팀 멤버 추가
    public void addMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }

    // 팀 멤버 제거
    public void removeMember(TeamMember teamMember) {
        this.teamMembers.remove(teamMember);
        teamMember.setTeam(null);
    }

    // 팀 멤버 수 조회
    public int getMemberCount() {
        return this.teamMembers.size();
    }
}