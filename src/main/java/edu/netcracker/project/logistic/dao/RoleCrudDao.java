package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Role;

import java.util.List;

public interface RoleCrudDao  extends CrudDao<Role, Long>{

   List<Role> getAllRole();
}
