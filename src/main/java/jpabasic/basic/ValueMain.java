package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ValueMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "zipcode");

            Member3 member = new Member3();
            member.setName("name");
            member.setHoemAddress(address);
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            em.persist(member);

            

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
