package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.AddressDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Address;
import edu.netcracker.project.logistic.service.QueryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;


@Repository
public class AddressDaoImpl implements AddressDao, QueryDao, RowMapper<Address> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ContactDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;



    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getLong("address_id"));
        address.setName(resultSet.getString("address_name"));
        return address;
    }

    @Autowired
    public AddressDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public Address save(Address address) {
        boolean hasPrimaryKey = address.getId() != null;

        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1, address.getId());
                ps.setObject(2, address.getName());
            });
        } else {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(psc -> {
                String query = getInsertQuery();
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, address.getName());
                return ps;
            }, keyHolder);
            Number key = (Number) keyHolder.getKeys().get("address_id");
            address.setId(key.longValue());
        }
        logger.info("Save address");
        return address;
    }

    @Override
    public void delete(Long aLong) {
        jdbcTemplate.update(getDeleteQuery(), ps -> ps.setObject(1, aLong));
        logger.info("Delete address");
    }


    @Override
    public Optional<Address> findOne(Long aLong) {
        try {
            Address address = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{aLong},
                    this);
            logger.info("Find address");
            return Optional.of(address);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Address findOne1(Long aLong) {

        Address address = jdbcTemplate.queryForObject(
                getFindOneQuery(),
                new Object[]{aLong},
                this);
        logger.info("Find1 address");
        return address;

    }

    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.address");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.address");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.address");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.address");
    }


}
