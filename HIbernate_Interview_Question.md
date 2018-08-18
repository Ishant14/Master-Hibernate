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

10. when u get following error?
a.  ObjectNotFoundException
b. NonUniqueObjectException
c. StaleStateException
11. what is HQL? in HQL is it directly possible to work with insert query?
12. in hibernate is it possible to work with procedure or function ?
13. do u know about (n+1) select problem?
14. tell me some strategy to solve (n+1) select problem?
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
