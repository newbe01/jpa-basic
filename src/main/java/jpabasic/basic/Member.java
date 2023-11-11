package jpabasic.basic;

import javax.persistence.*;
import java.util.Date;

@Entity // JPA가 관리하는 객체
//@Table(name = "Member")  실제 DB 테이블명 mapping
public class Member {

    @Id
    private Long id;

    @Column(name = "name") // domain의 속성을 정할 수 있음
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)    // EnumClass 를 사용할때 항상 옵션을 String 으로
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob    // bigSize
    private String description;

    @Transient  //  db 저장 X
    private int tmp;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
