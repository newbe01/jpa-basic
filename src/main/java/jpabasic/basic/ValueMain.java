package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ValueMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "zipcode");
            Address address2 = new Address("oldCity", "oldStreet", "oldZipcode");

            Member3 member = new Member3();
            member.setName("name");
            member.setHomeAddress(address);
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

            //값 타입들은 라이프사이클이 엔티티에 종속됨
            member.getFavoriteFoods().add("food1");
            member.getFavoriteFoods().add("food2");
            member.getFavoriteFoods().add("food3");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "1000"));

            em.persist(member);


            // 값 타입들은 전부 lazy loading
            Member3 findMem = em.find(Member3.class, member.getId());

            // 값타입은 수정이 불가능 삭제하고 새로 등록
//            findMem.getFavoriteFoods().remove("food1");
//            findMem.getFavoriteFoods().add("food4");

//            findMem.getAddressHistory().remove(new Address("oldCity", "oldStreet", "oldZipcode"));
//            findMem.getAddressHistory().add(new Address("oldCity2", "oldStreet", "oldZipcode"));

//            em.remove(member);  // 값타입들까지 전부 삭제 cascade + orphanRemoval

//            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
//            member.setHomeAddress(newAddress);

            // equals & hashcode 를 구현한뒤에는 true
//            System.out.println("add1 eq add2 = " + address.equals(address2));




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
