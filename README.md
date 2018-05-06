# Master-Hibernate

This repository contains the tips and tricks of hibernate which we generally used in our day to day work.

When using spring jdbc , sometimes we need to convert the rowmapper into the resultant entity, earlier we used to do that by 
manually writing row mapper(RowMapper) but in spring framework we can directly use **BeanPropertyRowMapper.class** as below

```java
    public List<Person> findAll(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
    }
```  
    
provided the entity in this Person class must have the same member name as we are expecting to get in the result.  

## Difference between @PersistentContext and @Autowired

@PersistenceContext allows you to specify which persistence unit you want to use. Your project might have multiple data sources connected to different DBs and @PersistenceContext allows you to say which one you want to operate on. @PersistenceContext is a JPA standard annotation designed for that specific purpose. Whereas @Autowired is used for any dependency injection in Spring. Using @PersistenceContext gives you greater control over your context as it provides you with ability to specify optional elements e.g. name, properties


>In Hibernate , simple native query are written using the tables in the database whereas the JPQL(Java Persistent Query Language ) are written using the Entities we create.

# OnetoOne Relationship

Suppose we have 2 tables one as **Student** and other as **Passport** and there is one to one relationship between these two tables.

Student table has column  :-  id(primary key), name

Passport table has column :-  id(primary key), number

To enable the **onetone** relationship there are two ways , either we define the relationship in student table or we define it in passport table.

We can define the onetone relationship in student table as below :

```java
@OneToOne
    private Passport passport;
```



           
