package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Office;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OfficeDao extends CrudDao<Office, Long> {

    List<Office> allOffices();
}
