# Master-Hibernate

This repository contains the tips and tricks of hibernate which we generally used in our day to day work.

When using spring jdbc , sometimes we need to convert the rowmapper into the resultant entity, earlier we used to do that by 
manually writing row mapper(RowMapper) but in we can directly use as below

    public List<Person> findAll(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
    }

