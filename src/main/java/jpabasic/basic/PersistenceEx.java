package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceEx {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            // 비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("name");

            // 영속상태
            em.persist(member);

            // 영속성 컨텍스트에서 분리, 준영속
            em.detach(member);

            // 객체를 삭제한 상태
            em.remove(member);

            // 실제 저장시점
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }
}
