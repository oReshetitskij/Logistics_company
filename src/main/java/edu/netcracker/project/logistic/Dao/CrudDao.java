package edu.netcracker.project.logistic.Dao;

import java.io.Serializable;

public interface CrudDao<T, ID extends Serializable> {


    void save(T  object);



    void delete(ID id);

    void  delete(T object);

    T find_one(ID id);

    boolean contains(ID id);
}
