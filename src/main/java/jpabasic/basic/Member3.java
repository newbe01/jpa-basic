package jpabasic.basic;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member3 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address hoemAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride( name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride( name = "zipcode", column = @Column(name = "WORK_ZIPCODE")),

    })
    private Address workAddress;

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHoemAddress() {
        return hoemAddress;
    }

    public void setHoemAddress(Address hoemAddress) {
        this.hoemAddress = hoemAddress;
    }
}
