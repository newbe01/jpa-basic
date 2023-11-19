package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class QueryMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // JPQL
            List<Member3> resultList = em
                    .createQuery("select m From Member3 m where m.name like '%kim%'", Member3.class)
                    .getResultList();

            // Criteria
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member3> query = cb.createQuery(Member3.class);
            Root<Member3> m = query.from(Member3.class);

            CriteriaQuery<Member3> eq = query.select(m).where(cb.equal(m.get("name"), "kim"));
            List<Member3> resultList1 = em.createQuery(eq).getResultList();

            // native SQL
            String sql = "select member_id, username from Member3";
            em.createNativeQuery(sql).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
