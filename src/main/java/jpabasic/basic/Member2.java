package jpabasic.basic;

import javax.persistence.*;

@Entity
public class Member2 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne // member 와 team 은 N : 1 관계
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 1:N 양방향시 readonly
    private Team2 team;

    public Team2 getTeam() {
        return team;
    }

    public void changeTeam(Team2 team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(Team2 team) {
        this.team = team;
    }
}
