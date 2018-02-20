package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.AdvertisementDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class AdvertisementDaoImpl implements AdvertisementDao, QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    public AdvertisementDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Advertisement> getMapper() {
        return (resultSet, i) ->
        {
            Advertisement a = new Advertisement();
            a.setId(resultSet.getLong("advertisement_id"));
            a.setName(resultSet.getString("name"));
            a.setDescription(resultSet.getString("description"));
            AdvertisementType type = new AdvertisementType();
            type.setId(resultSet.getLong("type_advertisement_id"));
            type.setName(resultSet.getString("advertisement_name"));
            a.setType(type);
            return a;
        };
    }

    @Override
    public Advertisement save(Advertisement object) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Advertisement> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public String getInsertQuery() {
        return null;
    }

    @Override
    public String getUpsertQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public String getFindOneQuery() {
        return null;
    }
}
