### Difference between load() vs get() method?

Hibernate session comes with different methods to load data from database. get and load are most used methods, at first look they seems similar but there are some differences between them.

- ```get()``` loads the data as soon as it’s called whereas load() returns a proxy object and loads data only when it’s actually required, so load() is better because it support lazy loading.
- Since ```load()``` throws exception when data is not found, we should use it only when we know data exists.
- We should use get() when we want to make sure data exists in the database.

More Details : https://www.journaldev.com/3472/hibernate-session-get-vs-load-difference-with-examples

### Difference between save() vs persist() method?



### Difference between update() vs merge()?
### Difference between hibernate vs jdbc?
### Difference between Session vs SessionFactory object?
### Is Session object is threadsafe?
### Is SessionFactory is threadsafe if yes then how?
### Hibernate domain object all states?
### Hibernate is throwing HibernateException so it is checked or unchecked?

Hibernate Exception is the RuntimeExcetion (Unchecked)
A history of exceptions — Exceptions and how they should be handled always end in heated debates between Java developers. It isn’t surprising that Hibernate has some noteworthy history as well. Until Hibernate 3.x, all exceptions thrown by Hibernate were checked exceptions, so every Hibernate API forced the developer to catch and handle exceptions. This strategy was influenced by JDBC , which also throws only checked exceptions. However, it soon became clear that this doesn’t make sense, because all exceptions thrown by Hibernate are fatal. In many cases, the best a developer can do in this situation is to clean up, display an error message, and exit the application. Therefore, starting with Hibernate 3.x, all exceptions thrown by Hibernate are subtypes of the unchecked Runtime Exception, which is usually handled in a single location in an application. This also makes any Hibernate template or wrapper API obsolete.

### When u get following error?
```a.  ObjectNotFoundException
b. NonUniqueObjectException
c. StaleStateException 
```
### what is HQL? in HQL is it directly possible to work with insert query?

Hibernate created a new language named Hibernate Query Language (HQL), the syntax is quite similar to database SQL language. ***The main difference between is HQL uses class name instead of table name, and property names instead of column name.***

- **HQL Select Query Example**

Retrieve a stock data where stock code is “7277”.

```SQL
Query query = session.createQuery("from Stock where stockCode = :code ");
query.setParameter("code", "7277");
List list = query.list();
```

- **HQL Update Query Example**

Update a stock name to “DIALOG1” where stock code is “7277”.

```SQL
Query query = session.createQuery("update Stock set stockName = :stockName" +
    				" where stockCode = :stockCode");
query.setParameter("stockName", "DIALOG1");
query.setParameter("stockCode", "7277");
int result = query.executeUpdate();
```

- **HQL Delete Query Example**

```SQL
Query query = session.createQuery("delete Stock where stockCode = :stockCode");
query.setParameter("stockCode", "7277");
int result = query.executeUpdate();
```
- **HQL Insert Query Example**

> **In HQL, only the INSERT INTO … SELECT … is supported; there is no INSERT INTO … VALUES. HQL only support insert from another table**.  For Example :

```SQL
"insert into Object (id, name) select oo.id, oo.name from OtherObject oo"; 

```

Insert a stock record from another backup_stock table. This can also called bulk-insert statement.

```SQL
Query query = session.createQuery("insert into Stock(stock_code, stock_name)" +
    			"select stock_code, stock_name from backup_stock");
int result = query.executeUpdate();
```
> The query.executeUpdate() will return how many number of record has been inserted, updated or deleted.

### In hibernate is it possible to work with procedure or function ?

Yes, it is possible to call stored procedure using Hibernate . See the explanaiton below :

MySQL store procedure

Here’s a MySQL store procedure, which accept a stock code parameter and return the related stock data.

```SQL
DELIMITER $$

CREATE PROCEDURE `GetStocks`(int_stockcode varchar(20))
BEGIN
   SELECT * FROM stock where stock_code = int_stockcode;
   END $$

DELIMITER ;

```

In MySQL, you can simple call it with a call keyword :
```SQL
CALL GetStocks('7277');
```
In Hibernate, there are three approaches to call a database store procedure.

- **1. Native SQL – createSQLQuery**

You can use **createSQLQuery()** to call a store procedure directly.

```java
Query query = session.createSQLQuery(
	"CALL GetStocks(:stockCode)")
	.addEntity(Stock.class)
	.setParameter("stockCode", "7277");
			
List result = query.list();
for(int i=0; i<result.size(); i++){
	Stock stock = (Stock)result.get(i);
	System.out.println(stock.getStockCode());
}
```

- **2. NamedNativeQuery in annotation**

Declare your store procedure inside the **@NamedNativeQueries** annotation.

```java
//Stock.java
...
@NamedNativeQueries({
	@NamedNativeQuery(
	name = "callStockStoreProcedure",
	query = "CALL GetStocks(:stockCode)",
	resultClass = Stock.class
	)
})
@Entity
@Table(name = "stock")
public class Stock implements java.io.Serializable {
...

```
Call it with **getNamedQuery().**

```java
Query query = session.getNamedQuery("callStockStoreProcedure")
	.setParameter("stockCode", "7277");
List result = query.list();
for(int i=0; i<result.size(); i++){
	Stock stock = (Stock)result.get(i);
	System.out.println(stock.getStockCode());
}
```

- **3. Call a Stored Procedure Using @NamedStoredProcedureQuery**

If you are using JPA 2.1 and the Hibernate implementation of the EntityManagerFactory and EntityManager.

The **@NamedStoredProcedureQuery** annotation can be used to declare a stored procedure:

```java
@NamedStoredProcedureQuery(
  name="GetAllFoos",
  procedureName="GetAllFoos",
  resultClasses = { Foo.class }
)
@Entity
public class Foo implements Serializable {
    // Model Definition 
}
```
To call our named stored procedure query, we need to have instantiated an **EntityManager**, and then call the **createNamedStoredProcedureQuery()** method to create the procedure:

```java
StoredProcedureQuery spQuery = 
  entityManager.createNamedStoredProcedureQuery("getAllFoos");

```
### Do u know about (n+1) select problem ?  Tell me some strategy to solve (n+1) select problem ?

Let's say you have a collection of ```Car``` objects (database rows), and each Car has a collection of Wheel objects (also rows). In other words, Car -> Wheel is a ```1-to-many``` relationship.

Now, let's say you need to iterate through all the cars, and for each one, print out a list of the wheels. The naive O/R implementation would do the following:

```SQL
SELECT * FROM Cars;
```

And then for each Car:

```SQL
SELECT * FROM Wheel WHERE CarId = ?
```

In other words, you have one select for the Cars, and then N additional selects, where N is the total number of cars.

**How to solve the N+1 problem ?**

Suppose we have a class Manufacturer with a many-to-one relationship with Contact.

We solve this problem by making sure that the initial query fetches all the data needed to load the objects we need in their appropriately initialized state. One way of doing this is using an HQL fetch join. We use the HQL

```java
"from Manufacturer manufacturer join fetch manufacturer.contact contact"
```

with the fetch statement. This results in an inner join:

```SQL
select MANUFACTURER.id from manufacturer and contact ... from 
MANUFACTURER inner join CONTACT on MANUFACTURER.CONTACT_ID=CONTACT.id
```

**Using a Criteria query we can get the same result from**

```java
Criteria criteria = session.createCriteria(Manufacturer.class);
criteria.setFetchMode("contact", FetchMode.EAGER);
```

which creates the SQL :

```SQL
select MANUFACTURER.id from MANUFACTURER left outer join CONTACT on 
MANUFACTURER.CONTACT_ID=CONTACT.id where 1=1
```
in both cases, our query returns a list of Manufacturer objects with the contact initialized. Only one query needs to be run to return all the contact and manufacturer information required


15. how many caches in hibernate? can u tell me differences between these caches?is they are automatically configured or we have to explicitly do some configuration?
16. did u worked with Criteria api in hibernate and in which situation we should go for it?
17. What is difference between getCurrentSession() and openSession() in Hibernate?

18. How do you log SQL queries issued by the Hibernate framework in Java application?
19. what is the use of dialect in hibernate?
20. how many types of mapping u know in hibernate?
21. what is Component ,Inheritance mapping and what is annotation we use for both these mapping?
22. what is association mapping in hibernate? how many types of association mapping possible in hibernate?

23. what is use of cascade attribute in hibernate?
24. what is orphan record in hibernate?
25. as u know there are 4 persistence ioperation(Create,Update,delete,insert) we use in hibernate so tell me which persistence operation is not generally used and with reason also?
26. tell me about many to many and one to one mapping and which annotation we use for onetoone association mapping?
27. which annotation u use to define foreign key column in hibernate?
28. which design pattern u observed in hibernate?
29. write hibernate configuration and mapping file code ?
30. which object represents connection object from database in hibernate?
31. which object shows connection pool in hibernate?
32. did u used properties file in hibernate if yes then tell me usecase?
33. what is use of hibernate.properties file?
34. what is lock in hibernate?what is difference between optimistic vs pessimistic lock?
35. what is use of Object versioning in hibernate?
36. what is use of timestamp in hibernate?
37. how u deal with images in hibernate?
38. did u know about hibernateFilter concept in hibernate?
39. in hibernate how u deal with Transaction management?
40. tell me what is main drawback of hibernate?
41  what is the mean of Synchronization?
42. where u used Singleton design pattern in hibernate?
43. what is online application and offline application and in which type of application we should use for hibernate and tell me the reason also?
44. how u enable second level cache in hibernate?
45. which is default connection provider in hibernate? and what is the issue to work with that?
46. in standalone application which type of connection pool u used in hibernate?
47. in web application which type of connection pool u used in hibernate?
48. what is proxool connection pool in hibernate?
49. write sample code for for association mapping so be prepare for writing sample code for all type of mappings also?
50. what is use of inverse attribute in hibernate with it's usage?
51. when we save parent in hibernate then it's child class also saved in database or not ?
52. when u delete any object in hibernate so what happens actually is really object is deleted?
