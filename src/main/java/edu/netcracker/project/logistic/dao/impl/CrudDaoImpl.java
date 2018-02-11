package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.CrudDao;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;


import java.io.Serializable;
import java.util.Optional;

public class CrudDaoImpl implements CrudDao {

    private JdbcTemplate jdbc;

    @Autowired
    CrudDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Autowired
    private QueryService queryService;

    @Override
    public Object save(Object object) {
        return null;
    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public Optional<Object> findOne(Serializable serializable) {
        return Optional.empty();
    }

    @Override
    public boolean contains(Serializable serializable) {
        return false;
    }
}
