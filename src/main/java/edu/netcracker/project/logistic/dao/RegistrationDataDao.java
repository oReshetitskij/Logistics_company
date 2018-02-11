package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RegistrationDataDao implements  CrudDao<RegistrationData, UUID> {
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

                        return regData;
                    }
            );
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(data);
    }

    // take a look
    @Override
    public Optional<RegistrationData> findOne(String username) {
        return Optional.empty();
    }

    @Override
    public boolean contains(UUID uuid) {
        Optional<RegistrationData> record = findOne(uuid);
        return record.isPresent();
    }
}
