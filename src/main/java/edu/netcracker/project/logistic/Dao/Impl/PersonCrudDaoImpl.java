package edu.netcracker.project.logistic.dao.Impl;

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
        return (resultSet, i) ->
        {
            Person person = new Person();
            person.setId(resultSet.getLong("person_id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setNickName(resultSet.getString("nick_name"));
            person.setPassword(resultSet.getString("password"));
            person.setRegistrationDate(resultSet.getDate("registration_date"));
            person.setEmail(resultSet.getString("email"));
            person.setPhoneNumber(resultSet.getString("phone_number"));
            return person;
        };
    }


    @Autowired
    PersonCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }


    @Override
    public Optional<Person> findOne(Long id) {
        Person person;
        String sql;
        try {
            sql = queryService.getQuery("select.person");

            person = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    getMapper());
            return Optional.of(person);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Person save(Person person) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        String sql;

        sql = queryService.getQuery("delete.person");

        jdbcTemplate.update(sql, aLong);
        System.out.println("Person with id: " + aLong + " successfully removed");
    }

    @Override
    public boolean contains(Long aLong) {
        return false;
    }
}
