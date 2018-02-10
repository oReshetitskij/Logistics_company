package edu.netcracker.project.logistic.Dao.Impl;

import edu.netcracker.project.logistic.Dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;

import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class PersonCrudDaoImpl implements PersonCrudDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;


        public RowMapper<Person> getMapper() {
            return (resultSet,i )->
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
                PersonCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService)
        {
            this.jdbcTemplate = jdbcTemplate;
            this.queryService = queryService;
        }


    @Override
    public Person find_one(Long id) {
    Person person;
     String sql;
        try {
            sql = queryService.getQuery("select.person");

            person = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    getMapper());
            return person;

        }catch (EmptyResultDataAccessException e){

        }
return  null;
    }


    @Override
    public Person save(Person person) {
      return  null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(Person object) {

    }



    @Override
    public boolean contains(Long aLong) {
        return false;
    }
}
