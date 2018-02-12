package edu.netcracker.project.logistic.dao.impl;

import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Role;
import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleCrudDaoImpl extends CrudDaoImpl<Role> implements RoleCrudDao {

  private JdbcTemplate jdbcTemplate ;
  private QueryService queryService;

    public RoleCrudDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

   private RowMapper<Role> getMapper()
 {
     return ((resultSet, i) ->
     {
       Role role = new Role();
       role.setRole_id(resultSet.getLong("role_id"));
       role.setRole_name(resultSet.getString("role_name"));
   return  role;

     });

 }

   private   RowMapper<Role> getMapper_Role_name()
    {
        return ((resultSet, i) ->
        {
            Role role = new Role();
            role.setRole_name(resultSet.getString("role_name"));
            return  role;

        });

    }

    @Override
    public Role save(Role role) {
        jdbcTemplate.update(getInsertQuery(),ps->{

            ps.setObject(1, role.getRole_id());
            ps.setObject(2, role.getRole_name());
        });

        return  role;
    }

    @Override
    public void delete(Long aLong) {
  jdbcTemplate.update(getDeleteQuery(),ps->
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

        }catch (EmptyResultDataAccessException e){
            System.err.println("Empty data");
        }
        return  Optional.empty();
    }

    @Override
    public List<Role> getAllRole() {
      return  jdbcTemplate.query(getAllRolesQuery(), getMapper_Role_name());
    }

    @Override
    public boolean contains(Long aLong) {
     Optional<Role> role = findOne(aLong);
        return role.isPresent();
    }

    @Override
    protected String getInsertQuery() {
        return queryService.getQuery("upsert.role");
    }

    @Override
    protected String getDeleteQuery() {
        return queryService.getQuery("delete.role");
    }

    @Override
    protected String getFindOneQuery () {
        return queryService.getQuery("select.role");
    }

    private String getAllRolesQuery()
    {
        return queryService.getQuery("role.findAll");
    }


}
