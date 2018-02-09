package edu.netcracker.project.logistic.Dao.Impl;

import edu.netcracker.project.logistic.Dao.CrudDao;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

public class CrudDaoImpl implements CrudDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

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
    public void delete(Object object) {

    }

    @Override
    public Object find_one(Serializable serializable) {

        return null;
    }

    @Override
    public boolean contains(Serializable serializable) {
        return false;
    }
}
