package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.AdvertisementDao;
import edu.netcracker.project.logistic.dao.AdvertisementTypeDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class AdvertisementDaoImpl implements AdvertisementDao, QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;
    private AdvertisementTypeDao advertisementTypeDao;

    @Autowired
    public AdvertisementDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService, AdvertisementTypeDao advertisementTypeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
        this.advertisementTypeDao = advertisementTypeDao;
    }

    @Override
    public Advertisement save(Advertisement advertisement) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        AdvertisementType advertisementType = advertisement.getType();
        String advertisementTypeName = advertisementType.getName();
        Optional<AdvertisementType> type = advertisementTypeDao.getByName(advertisementTypeName);
        if (type.isPresent()) {
            advertisement.setType(type.get());
        }
        jdbcTemplate.update(psc -> {
            String query = getInsertQuery();
            PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, advertisement.getName());
            ps.setObject(2, advertisement.getDescription());
            ps.setObject(3, LocalDateTime.now());
            ps.setObject(4, advertisement.getType().getId());
            return ps;
        }, keyHolder);
        Number key = (Number) keyHolder.getKeys().get("advertisement_id");
        advertisement.setId(key.longValue());

        return advertisement;
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
        return queryService.getQuery("insert.advertisement");
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
