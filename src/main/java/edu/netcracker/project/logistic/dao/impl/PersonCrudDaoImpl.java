package edu.netcracker.project.logistic.dao.impl;


import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;

import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class PersonCrudDaoImpl implements PersonCrudDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;


        protected RowMapper<Person> getMapper() {
            return (resultSet,i )->
            {
                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setNickName(resultSet.getString("nick_name"));
                person.setPassword(resultSet.getString("password"));
                person.setRegistrationDate(resultSet.getDate("registration_date").toLocalDate());
                person.setEmail(resultSet.getString("email"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                return person;
            };
    }



        @Autowired
                PersonCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService)
        {
            this.jdbcTemplate = jdbcTemplate;
            this.queryService = queryService;
        }




    @Override
    public Person save(Person person) {

        String sql =queryService.getQuery("insert.person");
            jdbcTemplate.update(sql,
                    new Object[]{person.getId(),
                            person.getFirstName(),
                            person.getLastName(),
                            person.getNickName(),
                            person.getPassword(),
                            person.getRegistrationDate(),
                            person.getEmail(),
                            person.getPhoneNumber()});
return person;
    }

    @Override
    public void delete(Long aLong) {
        String sql;

            sql = queryService.getQuery("delete.person");

            jdbcTemplate.update(sql, aLong);

    }

    @Override
    public Optional<Person> findOne(Long aLong) {
        Person person;
        String sql;

        try {
            sql = queryService.getQuery("select.person");

            person = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{aLong},
                    getMapper());
            return Optional.ofNullable(person);

        }catch (EmptyResultDataAccessException e){
            System.err.println("Empty data");
        }
        return  Optional.empty();
    }


    @Override
    public boolean contains(Long aLong) {
       Optional<Person> person  =findOne(aLong);
       return person.isPresent();
    }
}
