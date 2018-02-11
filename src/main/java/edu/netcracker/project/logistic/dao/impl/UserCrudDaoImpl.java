package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.UserCrudDao;
import edu.netcracker.project.logistic.model.User;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserCrudDaoImpl implements UserCrudDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    @Autowired
    UserCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public User save(User object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findOne(Long id) {

        User user;
        String sql;

        try {
            sql = queryService.getQuery("select.user");

            user = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    (resultSet, rowNum) -> {
                        User userTemp = new User();
                        userTemp.setId(resultSet.getLong(1));
                        userTemp.setFirstName(resultSet.getString(2));
                        userTemp.setLastName(resultSet.getString(3));
                        userTemp.setNickName(resultSet.getString(4));
                        userTemp.setPassword(resultSet.getString(5));
                        userTemp.setRegistrationDate(resultSet.getDate(6).toLocalDate());
                        userTemp.setEmail(resultSet.getString(7));
                        userTemp.setPhoneNumber(resultSet.getString(8));
                        return userTemp;
                    }

            );

            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean contains(Long aLong) {
        return false;
    }
}
