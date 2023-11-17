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
            member.setHomeAddress(address);
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            em.persist(member);

            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);



//            Member3 member2 = new Member3();
//            member2.setName("name2");
//            member2.setHomeAddress(copy);   // address 복사해서 사용해야함
//            member2.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
//            em.persist(member2);


//            member.getHomeAddress().setCity("newCity"); // member2 의 city 도 함게 변경
                                                        // set을 막아 변경하지 못하도록 불변적용

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
