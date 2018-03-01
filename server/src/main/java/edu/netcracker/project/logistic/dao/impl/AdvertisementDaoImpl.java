package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.AdvertisementDao;
import edu.netcracker.project.logistic.dao.AdvertisementTypeDao;
import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AdvertisementDaoImpl implements AdvertisementDao, QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;
    private AdvertisementTypeDao advertisementTypeDao;
    private RowMapper<AdvertisementType> advertisementTypeRowMapper;

    @Autowired
    public AdvertisementDaoImpl(JdbcTemplate jdbcTemplate,
                                QueryService queryService,
                                AdvertisementTypeDao advertisementTypeDao,
                                RowMapper<AdvertisementType> advertisementTypeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
        this.advertisementTypeDao = advertisementTypeDao;
        this.advertisementTypeRowMapper = advertisementTypeRowMapper;
    }

    private RowMapper<Advertisement> getMapper() {
        return (resultSet, i) ->
        {
            Advertisement advertisement = new Advertisement();
            advertisement.setId(resultSet.getLong("advertisement_id"));
            advertisement.setCaption(resultSet.getString("caption"));
            advertisement.setDescription(resultSet.getString("description"));
            advertisement.setPublicationDate(resultSet.getTimestamp("publication_date").toLocalDateTime());

            AdvertisementType advertisementType = advertisementTypeRowMapper.mapRow(resultSet, i);
            advertisement.setType(advertisementType);

            return advertisement;
        };
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
            ps.setObject(1, advertisement.getCaption());
            ps.setObject(2, advertisement.getDescription());
            ps.setObject(3, LocalDateTime.now());
            ps.setObject(4, LocalDateTime.now().plusWeeks(2));
            ps.setObject(5, advertisement.getType().getId());
            return ps;
        }, keyHolder);
        Number key = (Number) keyHolder.getKeys().get("advertisement_id");
        advertisement.setId(key.longValue());

        return advertisement;
    }

    @Override
    public void update(Advertisement advertisement){

        AdvertisementType advertisementType = advertisement.getType();
        String advertisementTypeName = advertisementType.getName();
        Optional<AdvertisementType> type = advertisementTypeDao.getByName(advertisementTypeName);
        if (type.isPresent()) {
            advertisement.setType(type.get());
        }
        jdbcTemplate.update(getUpdateQuery(),
                advertisement.getCaption(),
                advertisement.getDescription(),
                advertisement.getType().getId(),
                advertisement.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(getDeleteQuery(), ps ->
        {
            ps.setObject(1, id);
        });
    }



    @Override
    public Optional<Advertisement> findOne(Long id) {

        try {
            Advertisement advertisement = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{id},
                    getMapper());
            return Optional.of(advertisement);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Advertisement> allOffices() {
        return jdbcTemplate.query(getAllAdvertisements(), getMapper());
    }

    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.advertisement");
    }

    @Override
    public String getUpsertQuery() {
        return null;
    }

    String getUpdateQuery() {
        return queryService.getQuery("update.advertisement");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.advertisement");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.advertisement");
    }

    public String getAllAdvertisements(){
        return queryService.getQuery("all.advertisements");
    }
}
