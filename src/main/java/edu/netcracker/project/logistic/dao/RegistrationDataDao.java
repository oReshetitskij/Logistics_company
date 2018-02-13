package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RegistrationDataDao implements CrudDao<RegistrationData, UUID> {
    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    public RegistrationDataDao(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public RegistrationData save(RegistrationData registrationData) {
        String query = queryService.getQuery("upsert.registration_data");
        jdbcTemplate.update(query, ps -> {
            ps.setObject(1, registrationData.getRegistrationDataId());
            ps.setObject(2, registrationData.getFirstName());
            ps.setObject(3, registrationData.getLastName());
            ps.setObject(4, registrationData.getUsername());
            ps.setObject(5, registrationData.getPassword());
            ps.setObject(6, registrationData.getEmail());
            ps.setObject(7, registrationData.getPhoneNumber());
            ps.setObject(8, registrationData.getRegistrationDate());
        });
        return registrationData;
    }

    @Override
    public void delete(UUID uuid) {
        String query = queryService.getQuery("delete.registration_data");
        jdbcTemplate.update(query, ps -> {
            ps.setObject(1, uuid);
        });
    }

    @Override
    public Optional<RegistrationData> findOne(UUID uuid) {
        String query = queryService.getQuery("select.registration_data");
        RegistrationData data;

        try {
            data = jdbcTemplate.queryForObject(
                    query,
                    new Object[]{uuid},
                    (resultSet, rowNum) -> {
                        RegistrationData regData = new RegistrationData();

                        regData.setRegistrationDataId((UUID) resultSet.getObject(1));
                        regData.setFirstName(resultSet.getString(2));
                        regData.setLastName(resultSet.getString(3));
                        regData.setUsername(resultSet.getString(4));
                        regData.setPassword(resultSet.getString(5));
                        regData.setEmail(resultSet.getString(6));
                        regData.setPhoneNumber(resultSet.getString(7));
                        regData.setRegistrationDate(
                                OffsetDateTime.ofInstant(
                                        Instant.ofEpochMilli(
                                                resultSet.getTimestamp(8).getTime()),
                                        ZoneOffset.UTC.normalized()
                                )
                        );
                        return regData;
                    }
            );
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(data);
    }

    @Override
    public boolean contains(UUID uuid) {
        Optional<RegistrationData> record = findOne(uuid);
        return record.isPresent();
    }
}
