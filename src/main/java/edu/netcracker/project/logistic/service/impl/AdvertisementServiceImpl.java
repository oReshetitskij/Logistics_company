package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.AdvertisementDao;
import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private AdvertisementDao advertisementDao;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementDao advertisementDao){
        this.advertisementDao = advertisementDao;
    }

    @Override
    public void save(Advertisement advertisement) {
        advertisementDao.save(advertisement);
    }
}
