# Master-Hibernate

This repository contains the tips and tricks of hibernate which we generally used in our day to day work.We will use the in memory H2 database for the data operations. We need to do the below configuration for that :

1) jdbc:h2:mem:testdb , need to be given in the jdbc url and sa will be the password.
2) In application.properties , we need to enable the H2 console as "spring.h2.console.enabled=true".

When using spring jdbc , sometimes we need to convert the rowmapper into the resultant entity, earlier we used to do that by 
manually writing row mapper(RowMapper) but in spring framework we can directly use **BeanPropertyRowMapper.class** as below

```java
    public List<Person> findAll(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
    }
```  
    
provided the entity in this Person class must have the same member name as we are expecting to get in the result.  

## Difference between @PersistentContext and @Autowired

**@PersistenceContext** allows you to specify which persistence unit you want to use. Your project might have multiple data sources connected to different DBs and @PersistenceContext allows you to say which one you want to operate on. **@PersistenceContext** is a **JPA** standard annotation designed for that specific purpose. Whereas **@Autowired** is used for any dependency injection in Spring. Using **@PersistenceContext** gives you greater control over your context as it provides you with ability to specify optional elements e.g. name, properties

```java 
@Autowired
    EntityManager entityManager;
```

```java 
@PersistenceContext
    EntityManager entityManager;
```
Both the above approach works.

>In Hibernate , simple native query are written using the tables in the database whereas the JPQL(Java Persistent Query Language ) are written using the Entities we create.

## OnetoOne Relationship

Suppose we have 2 tables one as **Student** and other as **Passport** and there is one to one relationship between these two tables.

> Student table has column  :-  id(primary key), name

> Passport table has column :-  id(primary key), number

To enable the **onetone** relationship there are two ways , either we define the relationship in student table or we define it in passport table.

We can define the onetone relationship in student table as below :

```java
@OneToOne
    private Passport passport;
```

> One to One relationship are eager fetch by default 


## Why do entity in Hibernate must have default constructor ?

**Hibernate** creates instance of entities using reflection it uses **Class.newInstance()** method, which require a no argument constructor to create an instance. It's effectively equivalent of new Entity(). This method throws **InstantiationException** if it doesn't found any no argument constructor in Entity class, and that's why it's advised to provide a no argument constructor.

## Why do read only method need a transaction during lazy loading of an entity ?

Lets sgtart with the below exmaple , in this we have two entity student and passport and passort is lazily loaded.

```java
public void getStudent(){
        Student student = entityManager.find( Student.class,2);
        logger.info("Student details : {}", student);
        logger.info("Passport details : {}", student.getPassport());
    }
```
Above method will give the LazyLoad initialization exception. The reason for that is as :-

For the line : ```java Student student = entityManager.find( Student.class,2); ```

We are using the entityManager and entityManager always has transaction with it  Once the find() method get complete the transaction is removed . 

Since the entity is lazily loaded , for the next line ```java student.getPassport()) ``` hibernate will try to fire a query and fetch the data but since no entity manager is there hence no transaction is there , so lazyload initilization exception is being thrown.


If we **@Transactional** on the top the method , then it will created the transaction for the whole method and hence no exception will be thrown.


## @ManytoOne and @OnetoMany relationship 

Suppose we have two tables one is **Course** and other is  **Review** . Let's suppose **Course** and **Review** have **OnetoMany**(one course can have many reviews but one review belongs to only one particular course).

Table are as below 

> Course :-  id, name
> Review :- id. reviewstart, description

To enable the ManytoOne and OnetoMany we define it as below :

```java
@OneToMany(mappedBy = "course")
    List<Review> reviews = new ArrayList<>();
```
In the above code we have degined in course table that one course belongs to many review that is why **@OnetoMany** we have use  and in review table we will define it as :-

```java 
@ManyToOne
    private Course course;
```

Below code will be used to add some review to the course :

```java
@Transactional
    public void addReviews(){
        Course course = entityManager.find(Course.class,100);
        logger.info("Reviews for course id : - {} are {}", course.getId(),course.getReviews());

        Review review1 = new Review("5", "Great Hands on stuff");

        course.addReview(review1);
        review1.setCourse(course);

        entityManager.persist(review1);
    }
 ````
 
 > **ManytoOne or OnetoMany relationship are by default LAZY fetch.**
 

##  ManytoMany Relationship

Suppose we have two Student and Course and lets assume there is ManytoMany relationship between them. 

> Student :- id, name
> Course :- id, name

To establish the ManytoMany relationship between these two tables we have different approaches as , we can insert column either in Student tables (id,name, course_id) or in Course table as (id, name, student_id)  but that is not the good approach since it will affect the normalisation of tables. Hence we will create another table which will store the relationship between student and courses as below :

> STUDENT_COURSE :- student_id, course_id

In the code to define the manytomany realtionship we will add @ManytoMany in both tables student and courses as below:

```java
@ManyToMany(mappedBy = "courses")
    private List<Student> stduents = new ArrayList<>();
````

and in Student table 

```java 
@ManyToMany
    @JoinTable(name="STUDENT_COURSE",
    joinColumns=@JoinColumn(name="STUDENT_ID"),
    inverseJoinColumns = @JoinColumn(name="COURSE_ID"))
    private List<Course> courses = new ArrayList<>();
    
```




    `
           
