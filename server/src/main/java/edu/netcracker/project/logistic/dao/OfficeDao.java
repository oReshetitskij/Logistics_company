package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Office;

import java.util.List;


public interface OfficeDao extends CrudDao<Office, Long> {
    Office findByDepartment(String department);
    List<Office> allOffices();
}
