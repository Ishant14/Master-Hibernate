create table person
(
    id integer not null,
    name varchar(255) not null,
    location varchar(255),
    birth_date timestamp,
    primary key(id)

);


insert into person (ID,NAME,LOCATION,BIRTH_DATE)
values(1000,'Ishant','Pune',sysdate());

insert into person (ID,NAME,LOCATION,BIRTH_DATE)
values(1001,'Prashant','Delhi',sysdate());

insert into person (ID,NAME,LOCATION,BIRTH_DATE)
values(1002,'Gaurav','New York',sysdate());