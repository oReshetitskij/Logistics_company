package edu.netcracker.project.logistic.Dao;

import java.io.Serializable;
import java.util.Optional;

public interface CrudDao<T , ID extends Serializable> {


    void save(T  object);

    void delete(ID id);

    Optional<T> find_one(ID id);

    boolean contains(ID id);
}
