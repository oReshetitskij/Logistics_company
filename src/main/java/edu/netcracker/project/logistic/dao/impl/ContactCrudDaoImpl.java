package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.ContactCrudDao;
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
import java.util.Optional;

@Repository
public class ContactCrudDaoImpl implements ContactCrudDao, QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    ContactCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Contact> getMapper() {
        return (resultSet, i) ->
        {
            Person person =new Person();
            Contact contact = new Contact();
            contact.setContactId(resultSet.getLong("contact_id"));
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setPhoneNumber(resultSet.getString("phone_number"));
            contact.setPerson_id(resultSet.getLong("person_id"));
            return contact;
        };
    }

    @Override
    public Contact save(Contact contact) {
            boolean hasPrimaryKey = contact.getContactId() != null;
        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1, contact.getContactId());
                ps.setObject(2, contact.getFirstName());
                ps.setObject(3,contact.getLastName());
                ps.setObject(4,contact.getPhoneNumber());
                ps.setObject(5, contact.getPerson_id());
            });
        } else {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                String query = getInsertQuery();
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, contact.getFirstName());
                ps.setObject(2,contact.getLastName());
                ps.setObject(3,contact.getPhoneNumber());
                ps.setObject(4, contact.getPerson_id());
                return ps;
            }, keyHolder);
            Number key = (Number)keyHolder.getKeys().get("contact_id");
            contact.setContactId(key.longValue());
        }

        return contact;
    }


    @Override
    public void delete(Long aLong) {
   jdbcTemplate.update(getDeleteQuery(),ps ->
        {
            ps.setObject(1, aLong);
        });
    }


    @Override
    public Optional<Contact> findOne(Long aLong) {
         Contact contact;
        try {
            contact = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{aLong},
                    getMapper());
            return Optional.of(contact);

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Empty data");
        }
        return Optional.empty();
    }


    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.contact");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.contact");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.contact");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.contact");
    }
}
