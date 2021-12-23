## Spring Annotation

> Java annotation 은 프로그램의 소스코드 안에 다른 미리 정의된 프로그램의 정보를 미리 약속된 형식으로 포함시키는 것이다.


애너테이션 요소는 반드시 **반환값**이 존재하고, 매개변수가 없는 **추상메소드** 형태를 가진다. **( 오버라이딩 하지 않아도 된다. )**



**애너테이션 요소의 규칙**

**1.** 요소의 타입은 String, enum, 애너테이션, Class, 기본형

**2.** 매개변수는 선언할 수 없다.

**3.** 예외를 사용할 수 없다.

**4.** 요소의 타입 매개변수 사용할 수 없다.



#### **표준 Annotation** 

> Java에서 기본적으로 제공하는 애너테이션

- @Override : 오버라이딩 

- @Deprecated : 사용하지 않을 것을 권장 

- @SuppressWarnings : 경고 메세지 나타나지 않게

- @SafeVarags : 지네릭스 타입의 가변인자 사용

- @FunctionalInterface :  함수형 인터페이스라는 것을 의미

- @Native : native메소드에서 참조되는 상수 앞에 붙인다.
   

#### Meta annotation

- @Target : 애너테이션 적용가능한 대상을 지정.  **default 값은 모든 대상**
  - **@Target(ElementType.FIELD)**로 지정해주면, 필드에만 어노테이션을 달 수 있습니다. 만약 필드 말고 다른부분에 어노테이션을 사용한다면 컴파일 때 에러가 나게 됩니다.
    - *ElementType.TYPE (class, interface, enum)*
    - *ElementType.FIELD (instance variable)*
    - *ElementType.METHOD*
    - *ElementType.PARAMETER*
    - *ElementType.CONSTRUCTOR*
    - *ElementType.LOCAL_VARIABLE*
    - *ElementType.ANNOTATION_TYPE (on another annotation)*
    - *ElementType.PACKAGE (remember package-info.java)*

- @Documented : 애너테이션 정보가 javadoc으로 작성된 문서에 포함되게 한다.

- @Inherited : 애너테이션이 자손 클래스에 상속되도록 한다.

- @Retention : 애너테이션이 유지되는 범위를 지정한다. 즉, annotation 지속 시간을 정한다
  - RetentionPolicy.SOURCE : 컴파일 후에 정보들이 사라집니다. 이 어노테이션은 컴파일이 완료된 후에는 의미가 없으므로, 바이트 코드에 기록되지 않습니다. 예시로는 @Override와 @SuppressWarnings 어노테이션이 있습니다.
  - RetentionPolicy.CLASS : default 값 입니다. **컴파일 타임** 때만 .class 파일에 존재하며, **런타임**때는 없어집니다. 바이트 코드 레벨에서 어떤 작업을 해야할 때 유용합니다. Reflection 사용이 불가능 합니다.
  - RetentionPlicy.RUNTIME : 이 어노테이션은 런타임시에도 .class 파일에 존재 합니다. 커스텀 어노테이션을 만들 때 주로 사용합니다. Reflection 사용 가능이 가능합니다.

- @Repeatable : 애너테이션이 반복해서 적용할 수 있게 한다. 

 애너테이션을 **정의**할 때 애너테이션의 **적용대상**, **유지기간을** 지정하는데 사용된다. 



##### Spring Annotation 구현을 해보자

- Annotation은 @Interface 로 정의해야한다.
- 파라미터 멤버들의 접근자는 **public or default** 이어야만 한다.
- 파라미터 멤버들은 byte,short,char,int,float,double,boolean,의 기본타입과 String, Enum, Class, 어노테이션만 사용할 수 있습니다.
- 클래스 메소드와 필드에 관한 어노테이션 정보를 얻고 싶으면, **리플렉션만** 이용해서 얻을 수 있습니다. 다른 방법으로는 어노테이션 객체를 얻을 수 없습니다.

