package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.PersonRoleDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.PersonRole;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonRoleDaoImpl implements PersonRoleDao, QueryDao {
    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    PersonRoleDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public PersonRole save(PersonRole personRole) {
        jdbcTemplate.update(getUpsertQuery(), ps -> {
            ps.setObject(1, personRole.getPersonId());
            ps.setObject(2, personRole.getRoleId());
        });
        return personRole;
    }

    @Override
    public void delete(PersonRole personRole) {
        jdbcTemplate.update(getDeleteQuery(), ps -> {
            ps.setObject(1, personRole.getPersonId());
            ps.setObject(2, personRole.getRoleId());
        });
    }

    @Override
    public Optional<PersonRole> findOne(PersonRole id) {
        try {
            PersonRole personRole = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    (resultSet, i) -> {
                        PersonRole pr = new PersonRole();
                        pr.setPersonId(resultSet.getLong("person_id"));
                        pr.setRoleId(resultSet.getLong("role_id"));

                        return pr;
                    });
            return Optional.of(personRole);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteMany(List<PersonRole> personRoles) {
        List<Object[]> batchParams =
                personRoles
                        .stream()
                        .map(pr -> new Object[]{pr.getPersonId(), pr.getRoleId()})
                        .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(
                getDeleteQuery(),
                batchParams
        );
    }

    @Override
    public String getInsertQuery() {
        return null;
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.person_role");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.person_role");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.person_role");
    }
}
