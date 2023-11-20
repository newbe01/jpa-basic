package com.example.jpql.ex;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            for(int i = 0 ; i < 100; i++) {

            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setUsername("name");
            member.setAge(14);
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);


            em.persist(member);
//            }
// JPQL 기본문법
//            TypedQuery<Member> fineMem = em.createQuery("select m from Member m", Member.class);    // type 이 명확할때
//            Query fineMem2 = em.createQuery("select m from Member m");
//            Object findOne = em.createQuery("select m from Member m").getSingleResult();    // 결과가 단 하나일때만 사용
//
//            List<Member> result = em
//                    .createQuery("select m from Member m where m.username = :name and m.age = ?1", Member.class)
//                    .setParameter("name", "kim")    // parameter bind
//                    .setParameter(1, 10)            // 순서바인딩 권장 X
//                    .getResultList();

            em.flush();
            em.clear();

// 프로젝션
//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            Member findMem = result.get(0); // 영속성 컨텍스트에서 관리함
//            findMem.setAge(20);

            //여러값 조회시 받는타입
//            List<Object[]> resultList
//                    = em.createQuery("select m.username, m.age from Member m").getResultList();
//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object o = resultList.get(0);
//            Object[] resultO = (Object[]) o;
//            Object[] resultO = resultList.get(0);
//            System.out.println("name = " + resultO[0]);
//            System.out.println("age = " + resultO[1]);

            // DTO 생성 후 매핑
//            List<MemberDTO> resultList1
//                    = em.createQuery("select new com.example.jpql.ex.MemberDTO(m.username, m.age) from Member m"
//                            , MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = resultList1.get(0);
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getAge());

// 페이징
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("resultList = " + resultList.size());
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1.toString());
//            }

// Join
//            String query = "select m from Member m left join m.team t on t.name = 'team'";
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

// JPQL TYPE
//            String query = "select m, 'HELLO', TRUE from Member m left join m.team t on t.name = 'team'" +
////                    "where m.memberType = com.example.jpql.ex.MemberType.ADMIN";  // enum 은 패키지까지 다 넣어줘야함
//                    "where m.memberType = :memType";  // enum 은 패키지까지 다 넣어줘야함
//            em.createQuery(query, Member.class)
//                    .setParameter("memType", MemberType.ADMIN)
//                    .getResultList();

// case
//            String query = "select" +
//                    "           case when m.age <= 10 then 'student' " +
//                    "                when m.age >= 60 then 'Senior' " +
//                    "           else 'normal' end as price," +
//                    "       coalesce(m.username, 'noname')," +
//                    "       nullif(m.username, 'ADMIN') as username " +
//                    "from Member m";
//            List<Object[]> resultList = em.createQuery(query).getResultList();
//            Object[] objects = resultList.get(0);
//            System.out.println("resultList = " + objects[0]);
//            System.out.println("resultList = " + objects[1]);
//            System.out.println("resultList = " + objects[2]);

// JPQL 기본함수

            String sql = "select 'a' || 'b', " +
                    "   concat('a', 'b'),  " +
                    "   substring(m.username, 2, 3), " +
                    "   trim(m.age) ," +
                    "   locate('de', 'abcdefg', 1) " +      //  index 찾기
                    "from Member m";
            em.createQuery(sql).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
