package edu.netcracker.project.logistic.dao.impl;


import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;

import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class PersonCrudDaoImpl extends CrudDaoImpl<Person> implements PersonCrudDao {

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
        jdbcTemplate.update(getInsertQuery(), ps -> {
            ps.setObject(1,   person.getId());
            ps.setObject(2,   person.getFirstName());
            ps.setObject(3,   person.getLastName());
            ps.setObject(4,   person.getNickName());
            ps.setObject(5,   person.getPassword());
            ps.setObject(6,   person.getRegistrationDate());
            ps.setObject(7,   person.getEmail());
            ps.setObject(8,   person.getPhoneNumber());
        });
        return person;
            }


    @Override
    public void delete(Long aLong) {

            jdbcTemplate.update(getDeleteQuery(), aLong);

    }

    @Override
    public Optional<Person> findOne(Long aLong) {
        Person person;
        try {
            person = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
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

    @Override
    protected String getInsertQuery() {
        return queryService.getQuery("upsert.person");
    }

    @Override
    protected String getDeleteQuery() {
        return  queryService.getQuery("delete.person");
    }

    @Override
    protected String getFindOneQuery() {
        return  queryService.getQuery("select.person");
    }


}
