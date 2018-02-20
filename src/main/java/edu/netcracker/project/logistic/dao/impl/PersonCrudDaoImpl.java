package edu.netcracker.project.logistic.dao.impl;


import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;

import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;


@Repository
public class PersonCrudDaoImpl implements PersonCrudDao, QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    PersonCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Person> getMapper() {
        return (resultSet, i) ->
        {
            Person person = new Person();
            person.setId(resultSet.getLong("person_id"));
            person.setUserName(resultSet.getString("user_name"));
            person.setPassword(resultSet.getString("password"));
            person.setRegistrationDate(resultSet.getTimestamp("registration_date").toLocalDateTime());
            person.setEmail(resultSet.getString("email"));

            Contact contact = new Contact();
            contact.setContactId(resultSet.getLong("contact_id"));
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setPhoneNumber(resultSet.getString("phone_number"));

            person.setContact(contact);

            return person;
        };
    }

    @Override
    public Person save(Person person) {
        boolean hasPrimaryKey = person.getId() != null;

        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1, person.getId());
                ps.setObject(2, person.getUserName());
                ps.setObject(3, person.getPassword());
                ps.setObject(4, person.getRegistrationDate());
                ps.setObject(5, person.getEmail());
            });
        } else {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                String query = getInsertQuery();
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, person.getUserName());
                ps.setObject(2, person.getPassword());
                ps.setObject(3, person.getRegistrationDate());
                ps.setObject(4, person.getEmail());
                return ps;
            }, keyHolder);
            Number key = (Number) keyHolder.getKeys().get("person_id");
            person.setId(key.longValue());
        }
        return person;
    }

    @Override
    public void delete(Long aLong) {
        jdbcTemplate.update(getDeleteQuery(), ps ->
        {
            ps.setObject(1, aLong);
        });

    }

    @Override
    public Optional<Person> findOne(Long aLong) {
        Person person;
        try {
            person = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{aLong},
                    getMapper());
            return Optional.of(person);

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Empty data");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> findOne(String username) {
        Person person;
        try {
            person = jdbcTemplate.queryForObject(
                    getFindOneByUsernameQuery(),
                    new Object[]{username},
                    getMapper());
            return Optional.ofNullable(person);

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Empty data");
        }
        return Optional.empty();
    }

    @Override
    public Set<String> findDuplicateFields(Person person) {
        List<Person> matches = jdbcTemplate.query(
                getFindByEmailOrUsernameQuery(),
                pss -> {
                    pss.setString(1, person.getEmail());
                    pss.setString(2, person.getUserName());
                },
                getMapper()
        );

        Set<String> duplicateFields = new HashSet<>();
        for (Person match: matches) {
            if (match.getUserName().equals(person.getUserName())) {
                duplicateFields.add("username");
            } else if (match.getEmail().equals(person.getEmail())) {
                duplicateFields.add("email");
            }
        }
        return duplicateFields;
    }

    @Override
    public List<Person> findAll() {
        try {
            return jdbcTemplate.query(
                    getFindAllQuery(),
                    getMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Person> findAllEmployees() {
        try {
            return jdbcTemplate.query(
                    getFindAllEmployeesQuery(),
                    getMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.person");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.person");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.person");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.person");
    }

    private String getFindAllQuery() { return queryService.getQuery("all.person"); }

    public String getFindAllEmployeesQuery() { return queryService.getQuery("select.person.employee"); }

    private String getFindOneByUsernameQuery() {
        return queryService.getQuery("select.person.by.username");
    }

    private String getFindByEmailOrUsernameQuery() { return queryService.getQuery("select.person.by.email.or.username"); }
}
