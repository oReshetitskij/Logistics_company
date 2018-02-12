package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.CrudDao;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;


import java.util.Optional;

public abstract class CrudDaoImpl<T extends Object> implements CrudDao<T, Long> {
    @Autowired
    private JdbcTemplate jdbc;


    @Autowired
    private QueryService queryService;

    @Override
    public T save(T object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<T> findOne(Long id) {
        return Optional.empty();
    }



    @Override
    public boolean contains(Long id) {
        return false;
    }
    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected   abstract  String getFindOneQuery();




}
