package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {

    void save(Role role);

    void delete(Long aLong);

    Optional<Role> findOne(Long aLong);

    boolean exists(Long aLong);

    String findAll();

    List<Role> findRolesByPersonId(Long id);
}
