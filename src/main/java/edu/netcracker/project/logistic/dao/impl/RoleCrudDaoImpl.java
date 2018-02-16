package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.QueryDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Role;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleCrudDaoImpl  implements RoleCrudDao,QueryDao {

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;

    public RoleCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    private RowMapper<Role> getMapper() {
        return ((resultSet, i) ->
        {
            Role role = new Role();
            role.setRoleId(resultSet.getLong("role_id"));
            role.setRoleName(resultSet.getString("role_name"));
            return role;

        });

    }

    private RowMapper<Role> getMapper_Role_name() {
        return ((resultSet, i) ->
        {
            Role role = new Role();
            role.setRoleName(resultSet.getString("role_name"));
            return role;

        });

    }

    @Override
    public Role save(Role role) {
        boolean hasPrimaryKey = role.getRoleId() != null;

        if (hasPrimaryKey) {
            jdbcTemplate.update(getUpsertQuery(), ps -> {
                ps.setObject(1, role.getRoleId());
                ps.setObject(2, role.getRoleName());
            });
        } else {
            String query = getInsertQuery();
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(psc -> {
                PreparedStatement ps = psc.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, role.getRoleName());

                return ps;
            }, keyHolder);
            Number key = (Number)keyHolder.getKeys().get("role_id");
            role.setRoleId(key.longValue());
        }
        return role;
    }

    @Override
    public void delete(Long aLong) {
        jdbcTemplate.update(getDeleteQuery(), ps ->
        {
            ps.setObject(1, aLong);
        });
    }


    @Override
    public Optional<Role> findOne(Long aLong) {
        Role role;
        try {
            role = jdbcTemplate.queryForObject(
                    getFindOneQuery(),
                    new Object[]{aLong},
                    getMapper());
            return Optional.ofNullable(role);

        } catch (EmptyResultDataAccessException e) {
            System.err.println("Empty data");
        }
        return Optional.empty();
    }

    @Override
    public List<Role> getAllRole() {
        return jdbcTemplate.query(getAllRolesQuery(), getMapper_Role_name());
    }

    @Override
    public boolean contains(Long aLong) {
        Optional<Role> role = findOne(aLong);
        return role.isPresent();
    }

    private String getAllRolesQuery() {
        return queryService.getQuery("role.name.user");
    }


    @Override
    public String getInsertQuery() {
        return queryService.getQuery("insert.role");
    }

    @Override
    public String getUpsertQuery() {
        return queryService.getQuery("upsert.role");
    }

    @Override
    public String getDeleteQuery() {
        return queryService.getQuery("delete.role");
    }

    @Override
    public String getFindOneQuery() {
        return queryService.getQuery("select.role");
    }
}
