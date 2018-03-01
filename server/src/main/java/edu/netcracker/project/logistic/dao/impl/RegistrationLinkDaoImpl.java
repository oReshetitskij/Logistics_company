package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.dao.RegistrationLinkDao;
import edu.netcracker.project.logistic.model.RegistrationLink;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RegistrationLinkDaoImpl implements RegistrationLinkDao, QueryDao {
    private QueryService queryService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RegistrationLinkDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RegistrationLink save(RegistrationLink registrationLink) {
        boolean hasPrimaryKey = registrationLink.getRegistrationLinkId() != null;

        if (hasPrimaryKey) {
            jdbcTemplate.update(getInsertQuery(), ps -> {
                ps.setObject(1, registrationLink.getRegistrationLinkId());
                ps.setObject(2, registrationLink.getPersonId());
            });
        } else {
            throw new IllegalArgumentException(
                    "Primary key generation for table 'registration_link' is not supported"
            );
        }
        return registrationLink;
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update(getDeleteQuery(), ps -> {
            ps.setObject(1, uuid);
        });
    }

    @Override
    public Optional<RegistrationLink> findOne(UUID uuid) {
        RegistrationLink registrationLink;
        try {
            registrationLink = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{uuid},
                    (resultSet, i) -> {
                        RegistrationLink link = new RegistrationLink();
                        link.setRegistrationLinkId(resultSet.getObject("registration_link_id", UUID.class));
                        link.setPersonId(resultSet.getLong("person_id"));
                        return link;
                    });
            return Optional.of(registrationLink);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.registration_link");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.registration_link");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.registration_link");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.registration_link");
    }
}
