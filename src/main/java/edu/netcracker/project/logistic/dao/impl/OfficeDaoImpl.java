package edu.netcracker.project.logistic.dao.impl;

import ch.qos.logback.classic.Logger;
import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Address;
import edu.netcracker.project.logistic.model.Office;
import edu.netcracker.project.logistic.service.QueryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;


@Repository
public class OfficeDaoImpl implements OfficeDao, QueryDao {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OfficeDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    public OfficeDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Office> getMapper() {
        return (resultSet, i) ->
        {
            Address address = new Address();
        Office office = new Office();
            office.setOfficeId(resultSet.getLong("office_id"));
            office.setName(resultSet.getString("name"));
            office.setAddress(address);

            return office;
        };
    }


    @Override
    public Office save(Office office) {
        boolean hasPrimaryKey = office.getOfficeId() != null;
        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1,  office.getOfficeId());
                ps.setObject(2,  office.getName());
                ps.setObject(3,  office.getAddress().getId());

            });
        } else {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                String query = getInsertQuery();
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, office.getName());
                ps.setObject(2, office.getAddress().getId());
                return ps;
            }, keyHolder);
            Number key = (Number) keyHolder.getKeys().get("office_id");
            office.setOfficeId(key.longValue());
        }
        logger.info("Office saved");
        return office;
    }

    @Override
    public void delete(Long aLong) {
        jdbcTemplate.update(getDeleteQuery(), ps ->
        {
            ps.setObject(1, aLong);
            logger.info("Office delete");
        });

    }

    @Override
    public Optional<Office> findOne(Long aLong) {
        Office office;
        try {
            office = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{aLong},
                    getMapper());
            logger.info("Find one office");
            return Optional.of(office);

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Empty data");
        }

        return Optional.empty();
    }
    @Override
    public List<Office> allOffices() {
        return  jdbcTemplate.query(getAllOffices(),getMapper()) ;
    }

    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.office");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.office");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.office");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.office");
    }

    private String getAllOffices()
    {
        return queryService.getQuery("all.office");
    }

}
