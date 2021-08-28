### JPA의 N+1 select 문제와 해결 방법

JPA의 대표적으로 발생하는 문제인 n+1 select 문제가 무엇인지 알아보고, 해결 방안을 생각해보자



##### Cat Entity

```java
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Owner owner;
  
  	...
}
```

##### Owner Entity

```java
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Cat> cats = new LinkedHashSet<>();

  	...
}
```

Cat은 ManyToOne 관계로 집사와 관계돼 있으며, Owner는 여러마리의 고양이를 소유한다.



#### N + 1 문제란?

> 연관 관계에서 발생하는 이슈로 연관 관계가 설정된 엔티티를 조회할 경우에 조회된 데이터 갯수(n) 만큼 연관관계의 조회 쿼리가 추가로 발생하여 데이터를 읽어오게 된다. 이를 N+1 문제라고 한다.
>
> 즉, 고양이 10마리를 소유한 집사 Owner를 조회 했을 때,  (FetchType.Eager, default 상태라 가정)
>
> 집사 조회 .... select 1회
>
> 집사의 고양이 [1] 조회 ... select 2회
>
> 집사의 고양이 [2] 조회 ... select 3회
>
> 집사의 고양이 [3] 조회 ... select 4회
>
> ...
>
> 집사의 고양이 [8] 조회 ... select 9회
>
> 집사의 고양이 [9] 조회 ... select 10회
>
> 집사의 고양이 [10] 조회 ... select 11회
>
> 이런식으로 총 1 + N 번의 select 문을 날리게 된다. 이는 더 거대한 데이터 베이스의 소유주를 조회했을 때 심각한 속도 저하를 야기한다. 



#### N + 1 Select Test Code

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NPlusOneTest {

    @Autowired
    CatRepository catRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void exmapleTest() {
        Set<Cat> cats = new LinkedHashSet<>();

        for (int i = 0; i < 10; i++) {
            cats.add(new Cat("cat: " + i));
        }

        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner("owner" + i);
            owner.setCats(cats);
            owners.add(owner);
        }
        ownerRepository.saveAll(owners);
        entityManager.clear();

        List<Owner> everyOwners = ownerRepository.findAll();
        Assertions.assertFalse(everyOwners.isEmpty());
    }
}
```

#### 실제 결과 log를 확인해보자

* 고양이 집사 Owner를 조회하는 Query 생성
* 고양이 집사 Owner의 소유 고양이를 조회하는 query가 row 만큼 호출된다.

```sql
Hibernate: select owner0_.id as id1_2_, owner0_.name as name2_2_ from owner owner0_
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
Hibernate: select cats0_.owner_id as owner_id3_0_0_, cats0_.id as id1_0_0_, cats0_.id as id1_0_1_, cats0_.name as name2_0_1_, cats0_.owner_id as owner_id3_0_1_ from cat cats0_ where cats0_.owner_id=?
```

