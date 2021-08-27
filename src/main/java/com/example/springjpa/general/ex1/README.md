### 객체 매핑하기

* @Entity: JPA가 관리할 객체
  * 엔티티라고 한다
* @Id: DB PK와 매핑 할 필드

```java
@Entity
public class Member {
  @Id
  private Long id;
  private String name;
  ...
}
```

```sql
create table Member (
	id BIGINT not null,
	name varchar(255),
	primary key (id)
)
```

> @Entity를 넣어놔야 JPA가 DB와 mapping 해준다.
>
> DB의 Member Table에 id가 실제 객체 Member의 id가 되는데, 원한다면 Annotation을 이용하여 DB 테이블에
>
> username을 객체의 필드 name과 매핑 시킬수 있다.



### Persistence.xml

- JPA 설정 파일
- /META-INF/persistence.xml에 위치
  - <img src="../../../../../../../../readmeImages/persistencexml.png" alt="jpa" style="zoom:33%;"/> 
  - XML 문서에 persistence unit 등이 있는데, database 접속 정보 등의 옵션을 설정할 수 있음.
  - Option:
    - jdbc driver
    - **hibernate.dialect**: javax.persistence로 시작하는건 jpa로 표준, hibernate로 시작하는건 hibernate로만 사용할때 가능
    - dialect - [**<u>데이터베이스 방언</u>**]
      - JPA는 특정 데이터베이스에 종속적이지 않은 기술
      - 각각의 데이터베이스가 제공하는 SQL 문법, 함수가 조금씩 다르다
        - 가변문자: MySQL은 VARCHAR, Oracle은 VARCHAR2
        - 문자열을 자르는 함수: SQL 표준은 SUBSTRING(), Oracle은 SUBSTR()
        - **페이징: MySQL은 Limit, Oracle은 ROWNUM**
      - 방언: SQL 표준을 지키지 않거나 특정 데이터베이스만의 고유한 기능
    - <img src="../../../../../../../../readmeImages/dialect.png" alt="jpa" style="zoom:53%;"/> 
    - 즉 dialect 설정으로 알맞는 database에 맞는 SQL query문을 JPA에서 작성해준다.

- javax.persistence로 시작: JPA 표준 속성
- hibernate로 시작: 하이버네이트 전용 속성



### 애플리케이션 개발

- 엔티티 매니저 팩토리 (Entity Manager Factory) 설정
  - <img src="../../../../../../../../readmeImages/EntityManager.png" alt="jpa" style="zoom:53%;"/> 
  - EntityManagerFactory는 Entity Manager를 만든다.
  - 트랜잭션 단위로 처리할 때마다 Entity Manager를 생성한다
  - Entity Manager Factory는 **<u>하나만</u>** 생성해서 애플리케이션 전체에서 공유해야한다.
  - Entity Manager는 Thread간에 공유하면 안된다, 즉 사용하고 버린다.
    - 사용자의 1회 비즈니스 로직에 한 번 Entity Manager를 사용하고 해당 Entity Manager는 버린다.
  - JPA의 모든 데이터 변경은 트랜잭션 안에서 실행한다.
- 엔티티 매니저 설정
- 트랜잭션
- 비즈니스 로직 (CRUD) 



