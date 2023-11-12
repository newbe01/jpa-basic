package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TeamMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team2 team = new Team2();
            team.setName("TeamA");
            em.persist(team);

            Member2 member = new Member2();
            member.setName("member");
            member.setTeamId(team.getId());
            em.persist(member);

            Member2 member2 = em.find(Member2.class, member.getId());
            Team2 team2 = em.find(Team2.class, member2.getTeamId());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
