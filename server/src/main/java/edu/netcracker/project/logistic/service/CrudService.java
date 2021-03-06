package edu.netcracker.project.logistic.service;

import java.io.Serializable;
import java.util.Optional;

public interface CrudService<T, ID extends Serializable> {

    void save(T object);

    void delete(ID id);

    Optional<T> findOne(ID id);

    default boolean contains(ID id) {
        return findOne(id).isPresent();
    }
}
