package edu.netcracker.project.logistic.Dao.Impl;

import edu.netcracker.project.logistic.Dao.CrudDao;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;


import java.io.Serializable;
import java.util.Optional;

public abstract class CrudDaoImpl implements CrudDao {

    private JdbcTemplate jdbc;

    @Autowired
    CrudDaoImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbc=jdbcTemplate;
    }

    @Autowired
   private QueryService queryService;

    @Override
    public void save(Object object) {


    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public Optional<Object> find_one(Serializable serializable) {

        return null;
    }

    @Override
    public boolean contains(Serializable serializable) {
        return false;
    }
    protected abstract String getInsertQuery();
}
