package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.AdvertisementType;

import java.util.Optional;

public interface AdvertisementTypeDao extends CrudDao<AdvertisementType, Long>  {

    Optional<AdvertisementType> getByName(String advertisementName);

}
