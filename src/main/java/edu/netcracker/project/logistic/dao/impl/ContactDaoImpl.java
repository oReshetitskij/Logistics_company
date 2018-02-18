package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Contact;
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
public class ContactDaoImpl implements ContactDao, QueryDao {
    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    public ContactDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Contact> getMapper() {
        return (resultSet, i) ->
        {
            Contact c = new Contact();
            c.setContactId(resultSet.getLong("contact_id"));
            c.setFirstName(resultSet.getString("first_name"));
            c.setLastName(resultSet.getString("last_name"));
            c.setPersonId(resultSet.getLong("person_id"));
            return c;
        };
    }
    @Override
    public Contact save(Contact contact) {
        boolean hasPrimaryKey = contact.getContactId() != null;

        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1, contact.getContactId());
                ps.setObject(2, contact.getFirstName());
                ps.setObject(3, contact.getLastName());
                ps.setObject(4, contact.getPhoneNumber());
                ps.setObject(5, contact.getPersonId());
            });
        } else {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                String query = getInsertQuery();
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, contact.getFirstName());
                ps.setObject(2, contact.getLastName());
                ps.setObject(3, contact.getPhoneNumber());
                ps.setObject(4, contact.getPersonId());
                return ps;
            }, keyHolder);
            Number key = (Number) keyHolder.getKeys().get("contact_id");
            contact.setContactId(key.longValue());
        }
        return contact;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(getDeleteQuery(), ps -> ps.setObject(1, id));
    }

    @Override
    public Optional<Contact> findOne(Long id) {
        try {
            Contact contact = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{id},
                     getMapper());
            return Optional.of(contact);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
