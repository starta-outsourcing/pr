package org.example.taskflowd.team.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
>>>>>>> 30384f3ce0caca069770bbeccf13d84f83a713a8
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflowd.common.entity.BaseEntity;
<<<<<<< HEAD
import org.example.taskflowd.teammember.entity.TeamMember;

import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 30384f3ce0caca069770bbeccf13d84f83a713a8


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

<<<<<<< HEAD

    //양방향 맞나?
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team(String name, String description) {
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