package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.model.Advertisement;

import java.util.List;

public interface AdvertisementService {

    void save(Advertisement advertisement);
    void delete(Long id);
    List<Advertisement> findAll();

}
