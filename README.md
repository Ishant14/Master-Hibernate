# Master-Hibernate

This repository contains the tips and tricks of hibernate which we generally used in our day to day work.

When using spring jdbc , sometimes we need to convert the rowmapper into the resultant entity, earlier we used to do that by 
manually writing row mapper(RowMapper) but in spring framework we can directly use **BeanPropertyRowMapper.class** as below

    public List<Person> findAll(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
    }
    
provided the entity in this Person class must have the same member name as we are expecting to get in the result.    
    


