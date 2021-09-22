package com.esi.msdiabete.controller;

import com.esi.msdiabete.DTO.ResponseGly;
import com.esi.msdiabete.entities.DataGlucose;
import com.esi.msdiabete.repositories.DataGlucoseRepository;
import com.esi.msdiabete.service.DataGlucoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/iot")
public class datacontroller {
    @Autowired
    DataGlucoseRepository dataGlucoseRepository;

    @Autowired
    DataGlucoseService dataGlucoseService;

    Long idp;

    @GetMapping("setPatient/{idp}")
    public Long setIdp(@PathVariable(name = "idp") Long idPatient){
        return idp = idPatient;
    }

    @GetMapping("")
    public List<DataGlucose> getdata(){
        return dataGlucoseService.getDataGlucose();
    }
    @GetMapping("{id}")
    public DataGlucose getdatabyId(@PathVariable(name = "id") Long idGly){
        return dataGlucoseService.getDataById(idGly);
    }
    @GetMapping("patient/{key}")
    public List<DataGlucose> getdatabyKey(@PathVariable(name = "key") String key){
        return dataGlucoseService.getDataByKey(key);
    }
    @GetMapping("patient/{key}/{date}")
    public List<DataGlucose> gatDataByDate(@PathVariable(name = "key") String key,
                                           @PathVariable(name = "date") String date){
        return dataGlucoseService.getDataByDate(key, date);
    }

    @GetMapping("/patient/add")
    public DataGlucose addNewValue(@RequestParam("year") int year,
                                   @RequestParam("month") int month,
                                   @RequestParam("day") int day,
                                   @RequestParam("hour") int hour,
                                   @RequestParam("minutes") int munites,
                                   @RequestParam("glucose") float glucose,
                                   @RequestParam("deviceKey") String key)
    {
        DataGlucose gly =  dataGlucoseService.addNewValue(key, year, month, day, hour, munites, glucose);
        return gly;
    }

}
