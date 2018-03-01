package edu.netcracker.project.logistic.dao;

import java.io.Serializable;
import java.util.Optional;

public interface CrudDao<T, ID extends Serializable> {

    T save(T  object);

    void delete(ID id);

    Optional<T> findOne(ID id);

    default boolean contains(ID id) {
        return findOne(id).isPresent();
    }
}
