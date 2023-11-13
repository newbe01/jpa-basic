package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class TeamMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team2 team = new Team2();
            team.setName("TeamA");
//            team.getMembers().add(member);  // 역방향 매핑은 db저장되지않음

            Member2 member = new Member2();
            member.setName("member");
//            member.setTeamId(team.getId());

            em.persist(team);
//            member.changeTeam(team);
            em.persist(member);

            team.addMember(member);
//            team.getMembers().add(member); // 양방향 매핑시 양쪽값 세팅

            em.flush();
            em.clear();

            Team2 findTeam = em.find(Team2.class, team.getId());
            List<Member2> members = findTeam.getMembers();
            for (Member2 m : members) {
                System.out.println("m.getName() = " + m.getName());
            }

//            Member2 member2 = em.find(Member2.class, member.getId());
//            Team2 team2 = em.find(Team2.class, member2.getTeamId());
//            Team2 findTeam = member2.getTeam();
//            System.out.println("findTeam.getName() = " + findTeam.getName());
//
//            List<Member2> members = findTeam.getMembers();
//
//            for (Member2 m : members) {
//                System.out.println("m.getName() = " + m.getName());
//            }


//            Team2 newTeam = em.find(Team2.class, 100L);
//            member2.setTeam(newTeam);   // team Update시 객체를 넣어서 수정

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
