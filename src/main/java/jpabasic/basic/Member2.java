package jpabasic.basic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member2 extends BaseEntity {

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

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();
//    다대다 사용지양으로 중간테이블 생성방법

    @OneToMany(mappedBy = "member2")
    private List<MemberProduct> memberProducts = new ArrayList<>();


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
