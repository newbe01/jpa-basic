# JPA
 - JavaPeristenceAPI
 - SQL 중심적인 개발에서 객체중심으로 개발이 가능해 진다.
 - 성능, 생산성과 유지보수, 패러다임의 불일치해결 등 많은효과를 볼 수 있다.

### JPA의 시작
 - EntityManagerFactory는 하나만생성해서 APK전체에서 공유한다.
 - EntityManager는 스레드간에 공유X
 - JPA의 모든 데이터변경은 트랜잭션 안에서 실행해야한다.

### 영속성 컨텍스트
 - JPA를 이해하는데 가장 중요한용어로, "엔티티를 영구저장하는 환경이라는뜻"
 - 논리적인 개념으로 EntityManager를 통해 영속성 컨텍스트에 접근한다.
 - 비영속, 영속, 준영속, 삭제 네가지의 생명주기를 가진다.
 - 1차캐시, 동일성, 변경감지, 지연로딩, 쓰기지연등의 이점이 존재한다.
 - 영속성 컨텍스트의 변경내용을 DB에 반영하는 `flush`가 있다.
 - `flush`발생시 변경감지, 수정된 엔티티 등록, 쿼리전송등이 발생한다.
 - JPQL쿼리 실행시 자동으로 flush가 호출된다.
 - `flush`는 영속성 컨텍스트를 비우지않고, 변경내용을 db에 동기화한다.

### 엔티티 매핑
###### @Entity
```agsl
    @Entity
    public class Entity {
   
    }
```
 - `@Entity`가 붙은 클래스는 JPA가 관리, 엔티티라한다. 
 - JPA를 사용해 테이블과 매핑할 클래스는 필수로 붙여야한다.
 - 기본생성자를 필수로 가져야하며, `final`, `enum`, `interface`,`inner` class 사용 X
 - 저장할 필드에 `final`을 사용하면 안된다.

###### 필드와 컬럼 매핑
 - `@Column`을 사용해 컬럼을 매핑할 수 있다.
 - `@Column` 의 속성들을 사용해 db컬럼의 이름, nullable, unique 등 제약을 걸 수있다.
 - `Enum`타입을 사용할 때 `@Enumerated(ㄷEnumType.STRING)`을 사용해줘야 한다.
 - 길이가 큰 자료형일 경우 `@Lob`를 사용해 만들 수 있다.

###### 기본 키 매핑
```
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```
 - 기본키를 직접 할당 할 때는 `@Id`만 사용하면된다.
 - 자동생성 할 때는 `@GeneratedValue()`를 사용해 만들 수 있다.

### 연관관계 매핑 기초
 - 객체를 테이블에 맞추어 데이터중심으로 모델링하면, 협력관계를 만들 수 없다.
 - 테이블은 외래키 조인을 사용해 연관된 테이블을 찾지만, 객체는 참조를 사용해 찾는다.
 - 객체를 양방향으로 참조하려면 단방향 영관관계를 2번 만들어주어야 한다.
 - 양방향 매핑시, 두 관계중 하나를 연관관계의 주인으로 지정해 한쪽에서만 값을 다룰수 있게, 다른쪽은 읽기만 가능하게 해야한다.
 - 주인이 아니면 `mappedBy`속성으로 주인을 지정해주어야한다. 외래키를 가진쪽을 주인으로 하는것이 좋다.
 - 단방향 매핑만으로 이미 연관관계 매핑은 완료. 양방향 매핑은 반대방향으로 조회기능이 추가.

### 다양한 연관관계 매핑
 - 테이블은 외래키 하나로 양쪽 조인이 가능해, 방향이라는 개념이 없음.
 - 객체는 참조용 필드가 있는 쪽으로만 참조가 가능. 한쪽만 참조하면 단방향, 양쪽이 참조하면 양방향

#### N : 1
##### 단방향
 - 가장많이 사용하는 연관관계, 반대는 1:N관계
##### 양방향
 - 외래키가 있는 쪽이 연관관계의 주인이며, 양쪽을 서로 참조하도록 개발

#### 1 : N
##### 단방향
 - 테이블 일대다 관계는 항상 N 쪽에 왜래키가 있으므로, 1:N 단방향은 1이 연관관계의 주인.
 - 객체와 테이블 차이 때문에 반대편 테이블의 외래키를 관리하는 특이한 구조.
 - `@JoinColumn`을 반드시 사용해 주어야한다.
 - 엔티티가 관리하는 외래키가 다른테이블에있고, 관리를 위해 추가로 Update를 해야한다.
 - 1:N 보다는 N:1을 사용하는것을 지향
##### 양방향
 - 공식적으로 존재하지 않는 매핑
 - `@JoinColumn(insertable=false, updatable=flase`를 사용
 - 읽기전용 필드를 사용해, 양방향처럼 사용하는 방법. N:1 양방향을 권장
 
##### 1 : 1
 - 반대 관계도 1:1 관계이다.
 - 외래키를 어느쪽에서도 설정할 수 있다.
 - 외래키에 DB unique 설정을 추가해야한다.

##### N : M
 - RDBMS는 테이블 2개로 다대다 관계를 표현할 수 없음.
 - `@ManyToMany`를 사용하고, `@JoinTable`로 연결을 지정한다.

### 고급매핑
##### 상속관계 매핑
 - RDBMS는 상속관계 X
 - 슈퍼타입 서브타입 관계라는 모델링기법이 객체의 상속과 유사하다.
 - 객체의 상속과 구조와 DB의 슈퍼,서브타입 관계를 매핑

##### 조인전략
 - 각각 테이블로 변환해서 조인하는방법
 - 테이블정규화, 저장공간 효율화, 외래키참조 무결성제약조건 활용등의 장점이있다.
 - 조회시 조인을 많이하고, 성능이 저하되며 쿼리가 복잡하다. 저장시 `insert`를 2번호출한다.

##### 단일테이블 전략
 - 통합 테이블로 변환하는방법
 - 조인이 필요없으므로 조회성능이 빠르고, 조회쿼리가 단순하다.
 - 자식 엔티티가 매핑한 컬럼은 모두 `null`허용을 해야하고, 테이블이 거대해질 수 있다. 상황에 따라 성능이 느려질 수 있다.

##### 구현클래스마다 테이블 전략 (권장 X)
 - 서브타입 테이블로 변환하는방법
 - 서브타입을 명확하게 구분해 처리할때 효과적이고, not null 제약조건을 사용할 수 있다.
 - 여러 자식테이블을 함께 조회할때 성능이 느리고, 자식테이블을 통합해서 쿼리하기 어렵다.

###### `@MappedSuperclass`
 - 상속관계 매핑 X, 엔티티 X, 테이블과 매핑 X
 - 부모클래스를 상속받는 자식클래스에 정보만을 제공한다. 조회, 검색이 불가능하다.
 - 직접 생성해서 사용할 일이 없으므로 추상클래스를 권장
 - 테이블과 관계가 없고, 단순 엔티티가 공통으로 사용하는 매핑정보를 모으는역할
 - `@Entity`클래스는 엔티티나, `@MappedSuperclass`로 지정한클래스만 상속가능 

### 프록시와 연관관계
###### 프록시
 - 실제 클래스를 상속받아 만들진다. 실제 클래스와 겉 모양이 같다.
 - 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면됨
 - 프록시 객체는 실제 객체의 참조(target)를 보관
 - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 매소드를 호출
 - 프록시 객체는 처음사용할 때 한번만 초기화한다. 이때 객체가 실제 엔티티로 변화는 X, 
초기화된 후 프록시 객체를 통해 실제 엔티티에 접근 가능.
 - 프록시객체는 원본엔티티를 상속받기에 타입체크시 주의해야함. (instance of를 사용)
 - 영속성 컨텍스트에 찾는 엔티티가 이미있다면, `em.getReference()`를 호출해도 실제 엔티티를 반환한다.
 - 준영속 상태일때, 프록시를 초기화하면 문제가 발생한다.

###### 즉시로딩과 지연로딩
 - 가급적 지연로딩만 사용하는것을 권장
 - 즉시로딩을 적용하면 예상치못한 SQL에러 발생할 수 있고, JQPQL에서 N+1 쿼리 문제를 일으킬 수 있다.

###### 지연로딩 활용
 - 모든연관관계에서 지연로딩을 권장
 - JPQL fetch조인이나, 엔티티그래프 기능을 사용하는것을 권장

###### 영속성 전이 : CASCADE
 - 특정 엔티티를 영속상태로 만들때 연관된 엔티티도 함께 영속상태로 만들고 싶을때 사용
 - 영속성전이는 연관관계를 매핑하는것과 아무관련이 없다
 - 엔티티를 영속화할때 연관된 엔티티도 함께 영속화하는 편리함을 제공한다

###### 고아객체
 - 부모엔티티와 연관관계가 끊어진 자식엔티티를 말함
 - 참조가 제거된 엔티티는 다른곳에서 참조하지않는 고아객체로보고 삭제하는기능
 - 참조하는곳이 하나일 때 사용해야하고, 특정엔티티가 개인소유할 때 사용한다.
 - `@OneToOne`, `@OneToMany`에서만 사용이 가능하다

###### 영속성 전이 + 고아객체, 생명주기
```
    CascadeType.ALL + orphanRemoval = true
```
 - 스스로 생명주기를 관리하는 엔티티는 `em.persist()`로 영속화, `em.remove()`로 제거한다
 - 두옵션을 모두 활성화하면 부모엔티티를 통해 자식의 생명주기를 관리할 수 있다.
 - 도메인 주도 설계(DDD)의 AAggregate Root 개념을 구현할 때 유용하다.

### 값 타입
##### 기본 값 타입
###### 엔티티 타입 
 - `@Entity`로 정의하는객체
 - 데이터가 변해도 식별자로 지속해서 추적가능

###### 값 타입
 - `int`, `Integer`, `String` 처럼 단순 값으로 사용하는 자바 기본 타입이나 객체
 - 식별자가 없고, 값만 있으므로 변경시 추적이 불가능
 1) 기본값 타입
    - `String`, `int`등 자바의 기본 값 타입(primitive type)
    - 생명주기를 엔티티의 의존
    - 기본타입은 절대 공유 X
    - 기본타입은 항상 값을 복사하고, `Integer`,`Long`같은 래퍼클래스나 `String`같은 특수 클래스도 공유는 가능하지만 변경 X
 2) 임베디드 타입
    - `@Embeddable`은 값 타입을 정의하는곳에, `@Embedded`은 값 타입을 사용하는곳에 표시한다.
    - 기본 생성자가 필수
    - 새로운 값 타입을 직접 정의할 수 있음
    - JPA는 임베디드 타입(embedded type) 이라고 한다
    - 주로 기본값 타입을 모아서 만들어서 복합 값 타입이라고도 함
    - `int`, `String` 과 같은 값 타입
    - 재사용, 높은 응집도, 해당 값 타입만 사용하는 의미있는 메소드를 만들 수 있다, 
    임베디드 타입을 포함한 모든 값 타입은 값 타입을 소유한 엔티티에 생명주기를 의존함 과 같은 장점이 있다.
    - 임베디드 타입은 엔티티의 값일 뿐, 임베디드 타입을 사용하기전과 후는 매핑하는 테이블은 같다.
    - 객체와 테이블을 아주 세밀하게(find-grained) 매핑하는것이 가능해진다
    - 잘 설계한 ORM 어플리케이션은 매핑한 테이블의 수보다 클래스의 수가 많다
    - 임베디드 타입의 값이 null이면 매핑한 컬럼값은 모두 null
 3) 컬렉션 값 타입

###### 값 타입과 불변객체
 - 값 타입은 복잡한 객체사상을 단순화하려고 만든 개념. 값 타입은 단순하고 안전하게 다룰수 있어야한다. 
   - 값 타입의 공유참조 
     - 임베디드 타입같은 값 타입을 여러 엔티티에서 공유하면 위험하다. 
     - 부작용이 발생할 수 있다.
   - 값 타입의 복사
     - 값 타입의 실제 인스턴스인 값을 공유하는것은 위함하다. 
     - 인스턴스를 복사해서 사용하는것을 권장
   - 값 타입의 한계
     - 항상 값을 복사해서 사용하면 공유참조로 인해 발생하는 부작용을 피할 수 있다.
     - 임베디드 타입처럼 직접 정의한 값타입은 자바의 기본타입이 아니라 객체타입이다.
     - 자바 기본타입에 값을 대입하면 값을 복사한다.
     - 객체타입은 참조값을 직접 대입하는 것을 막을방법이 없다.
     - 객체의 공유참조는 피할 수 없다.
   - 불변 객체
     - 불변객체는 생성시점 이후 절대 값을 변경할 수 없는 객체
     - 객체타입을 수정할 수 없게 만들면 부작용을 원천 차단
     - 값 타입은 불변 객체(immutable object)로 설계해야함
     - 생성자로만 값을 설정하고, 수정자를 만들지 않으면 된다.

###### 값 타입의 비교
 - 인스턴스가 달라고 그 안에 값이 같으면 같은것으로 봐야한다.
 - 동일성(identity) 비교 : 인스턴스의 참조값을 비교, `==` 사용
 - 동등성(equivalence) 비교 : 인스턴스의 값을 비교, `equals()` 사용
 - 값 타입 비교는 `equals()`메소드를 적절히 재 정의해 동등성 비교를 해야한다. 

###### 값 타입 컬렉션
 - 값 타입을 하나이상 저장할 때 사용
 - `@ElementCollection`, `@ColletionTable` 등을 사용
 - DB는 컬렉션을 같은 테이블에 저장할 수 없다.
 - 컬렉션을 저장하기 위한 별도의 테이블이 필요하다
 - 값 타입 컬렉션은 영속성 전이(Cascade) + 고아객체 제거 기능을 필수로가진다고 볼 수 있다.
   - 값 타입 컬렉션의 제약사항
     - 값 타입은 엔티티와 다르게 식별자 개념이 없다.
     - 값은 변경하면 추적이 어렵다
     - 값타입 컬렉션에 변경사항이 발생하면, 주인엔티티와 연관된 모든 데이터를 삭제하고, 
     값 타입 컬렉션에 있는 현재값을 모두 다시 저장한다.
     - 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 한다. null X, 중복저장 X
   - 값 타입 컬렉션 대안
     - 값 타입컬렉션 대신, 일대다 관계를 고려한다. 관계를 위한 엔티티를 만들고, 여기서 값 타입을 사용
     - 영속성 전이 + 고아객체 제거를 사용해서 값 타입 컬렉션처럼 사용

###### 값 타입 정리
 - 엔티티 타입의 특정
   - 식별자 O
   - 생명주기 관리
   - 공유
 - 값 타입의 특징
   - 식별자 X
   - 생명주기를 엔티티에 의존
   - 공유하지 않는것이 안전(복사해서 사용)
   - 불변객체로 만드는것이 안전
 - 값 타입은 정말 값 타입이라 판단될때만 사용
 - 엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 안된다
 - 식별자가 필요하고, 지속해서 값을 추적, 변경해야한다면 그것은 값타입이 아닌 엔티티이다.
