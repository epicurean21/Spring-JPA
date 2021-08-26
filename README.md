# Spring JPA

> 김영한 저자의 '자바 ORM 표준 JPA 프로그래밍' 및 강의를 바탕으로 
> Spring JPA를 공부한다.
>
> <img src="./readmeImages/jpa.jpg" alt="jpa" style="zoom:33%;" /> 

### Server Spectiication
- IntelliJ (Mac OS X)
- Java 11
- H2 Database
- JPA



### JPA (Java Persistence API)

##### 왜 JPA를 사용할까?

> Database는 아직까지 관계형 DB - [Orable, MySQL, MariaDB] 등을 사용한다.
>
> * 지금 시대는 **객체를 관계형 DB에** 관리한다.
>
> 
>
> 관계형 DB는 SQL을 계속해서 작성해야한다.



1. SQL 중심적인 개발의 문제점

- CRUD의 무한 반복

  > 무한반복하며 지루한 코드의 연속

  ```java
  public class Member {
    private String memberId;
    private String name;
  
    ...
      
  }
  ```
  
  ```sql
  INSERT INTO Member (Member_ID, NAME) VALUES
  SELECT MEMBER_ID, NAME FROM MEMBER M
  UPDATE MEMBER SET ...
  ```

* 여기서 만약 Member의 '연락처'가 추가된다면? - [필드 추가]
  * 연락처 tel 변수가 추가되며 **모든 쿼리를 수정해주어야한다**.
  * 쿼리수정시 실수가 발생하면 정상작동하지 않는다.

* 엔티티 신뢰 문제

  ```java
  class MemberService {
  	  ...
      
      private void process(String id) {
        Member member = memberDAO.find(id);
        member.getTeam(); // ??
        member.getOrder().getDelivery(); // ???
      }
  }
  ```

  객체지향적인 코드에서 Member가 어떤 Team에 속한다면,

  MemberDAO에서 find해서 member 변수를 받아오고, 그 멤버 객체에서 .getTeam()으로 팀을 가져온다.

  1. Member가 소속된 팀 객체를 가져오는것
  2. Member가 주문된 정보, 주문의 배송지 정보를 가져오는 것

  이것이 불가능하다.

  Member DAO에서 Member 객체를 가져오는데, Team과 Order, Delivery의 연관에 대한 모든것을 가져와야한다.

  즉, MemberDAO에서 query가 Team, Order, Delivery를 다 가져와야한다. 

  보장이 되지 않는다면 이러한 코드 작성이 불가능하다.

> 계층형 아키텍처 - [Controller, Service, Repository, DAO ... ]
>
> 진정한 의미의 **계층 분할이** 어렵다.



* 패러다임의 불일치 - 객체 vs. 관계형 데이터베이스

> 객체지향과 관계형 데이터베이스는 사상이 다르다.
>
> 관계형 데이터베이스는 철저하게 데이터를 잘 저장할지에 Focus 되어있다.
>
> 이 두 가지를 억지로 Mapping해서 작업을 해야한다는 번거로움이 있다.
>
> '객체 지향 프로그래밍은 추상화, 캡슐화, 정보은닉, 상속, 다향성 등 시스템의 복잡성을 제어할 수 있는 다양한 장치들을 제공한다'



객체를 영구 보관하는 다양한 저장소 - [Object] 

- RDB
- NoSQL
- File
- OODB

> 현실적으로 RDB, NoSQL을 사용하기에 현실적으로는 관계형 DB를 사용한다.

<img src="./readmeImages/JPA2.png" alt="jpa" style="zoom:33%;" /> 

- 객체를 SQL로 **우리가** 바꾸고, 저장을 해야한다.
- 즉, 개발자 == SQL 매퍼 역할이었다.



##### 그렇다면 근본적으로 객체와 관계형 데이터베이스는 무슨 차이가 있을까?

1. 상속

   <img src="./readmeImages/inheritance.png" alt="jpa" style="zoom:33%;" /> 

   - 관계형 데이터베이스에는 상속 관계가 있을까?
     - 있는 데이터베이스도 있지만, 당연히 없다.
     - 객체는 있는데 이거를 어떻게 풀어낼까?
   - Album을 저장한다 해보자.
     - 객체 분해
     - INSERT INTO ITEM ...
     - INSERT INTO ALBUM ... 
     - 최소 두 개의 Query 작성을 해야한다. 
   - 조회는 어떻게 할것인가?
     - Item과 Album을 JOIN해서 가져온다. Select ~ Item Join Album
     - **문제는** Movie, Book은??? 
       - **그때마다 새로운 join 쿼리를 작성한다**
     - 그래서 DB는 상속관계 없이, Super ITEM DTO를 만들어서 다 들어있는걸 사용한다...



1. 연관관계
2. 데이터 타입
3. 데이터 식별 방법

 

