package com.esi.msdiabete.repositories;


import com.esi.msdiabete.entities.DataGlucose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DataGlucoseRepository extends JpaRepository<DataGlucose, Long> {
    public List<DataGlucose> findDataGlucoseByDeviceKey(String key);
    public List<DataGlucose> findDataGlucoseByDeviceKeyAndDateEnregistrement(String key, String date);
}
