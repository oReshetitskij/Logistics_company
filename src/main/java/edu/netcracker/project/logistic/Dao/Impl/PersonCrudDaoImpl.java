package edu.netcracker.project.logistic.Dao.Impl;

import edu.netcracker.project.logistic.Dao.PersonCrudDao;
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
    public Optional<Person> find_one(Long id) {
    Person person;
     String sql;

            sql = queryService.getQuery("select.person");

            person = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    getMapper());
            return Optional.ofNullable(person);
    }



    @Override
    public void save(Person person) {

        String sql =queryService.getQuery("insert.person");

       jdbcTemplate.update(sql,
               new Object[]{person.getId() ,
               person.getFirstName(),
               person.getLastName(),
               person.getNickName(),
               person.getPassword(),
               person.getRegistrationDate(),
               person.getEmail(),
               person.getPhoneNumber()});


    }

    @Override
    public void delete(Long aLong) {
        String sql;

            sql = queryService.getQuery("delete.person");

            jdbcTemplate.update(sql, aLong);

    }

    @Override
    public void delete(Person object) {

    }



    @Override
    public boolean contains(Long aLong) {
        return false;
    }
}
