package com.example.jpql.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("name1");
            member.setAge(14);
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);

            Member member2 = new Member();
            member2.setUsername("name2");
            member2.setAge(15);
            member2.setMemberType(MemberType.ADMIN);
            member2.setTeam(team);

            Member member3 = new Member();
            member3.setUsername("name3");
            member3.setAge(16);
            member3.setMemberType(MemberType.ADMIN);
            member3.setTeam(team2);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

//  경로 표현식
//            String sql = "select m.username from Member m";     //상태필드, 더이상 탐색 불가능
//            String sql = "select m.team from Member m";        // 단일값 연관경로, 묵시적 내부조인 발생 추가 탐색가능 team.name~
//            String sql = "select t.members.size from Team t";        // 컬렉션 값 연관경로, 묵시적 내부조인 발생, 추가 탐색 X
//            String sql = "select m from Team t join t.members m";      // 명시적 조인, 추가 탐색가능
//            em.createQuery(sql).getResultList();

//  fetch join
//  연관된 엔티티도 함께조회(즉시로딩) 객체그래프를 한번에 조회하는개념
//            String sql = "select m from Member m join fetch m.team";
//            String sql = "select t From Team t";
//            em.createQuery(sql).setFirstResult(0).setMaxResults(2).getResultList()    //batch size 와 함께 아래쿼리 페이징
//            String sql = "select distinct t from Team t join fetch t.members";  // 컬렉션의 중복도 함께 제거
//            List<Team> resultList = em.createQuery(sql, Team.class).getResultList();
//
//            for (Team findTeam : resultList) {
//                System.out.println("member = " + findTeam.getName() +"  ,"+ findTeam.getMembers().size());
//
//                for (Member m : findTeam.getMembers()) {
//                    System.out.println("m = " + m);
//                }
//            }

//  Entity 직접사용
//            String sql = "select m from Member m where m.id = :memberId";
//            String sql = "select m from Member m where m = :member";    // 따로 값 지정안할시 기본키로 동작
            String sql = "select m from Member m where m.team = :team";     // 외래키값은 Entity에 매핑한 값이 기본키로 동작
            List<Member> member1 = em.createQuery(sql, Member.class).setParameter("team", team).getResultList();
            for (Member findMem : member1) {
                System.out.println("findMem = " + findMem);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
