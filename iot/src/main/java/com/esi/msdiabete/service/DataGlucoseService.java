package com.esi.msdiabete.service;

import com.esi.msdiabete.entities.DataGlucose;
import com.esi.msdiabete.repositories.DataGlucoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataGlucoseService {

    @Autowired
    DataGlucoseRepository dataGlucoseRepository;

    @Autowired
    RestTemplate restTemplate;

    /* public static String getBearerTokenHeader() {
         return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
     }
     public ResponseTemplate getDataGlucoseWithPatient(Long idDG){
         ResponseTemplate rt = new ResponseTemplate();
         DataGlucose dg = dataGlucoseRepository.findById(idDG).orElse(null);

         String token = getBearerTokenHeader();

         HttpHeaders headers = new HttpHeaders();
         headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
         headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
         headers.set("Authorization", token);
         HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);

         // use Rest template to consume rest api
         // use headers to generate jwtEntity
         //return response in PatientDTO format

         ResponseEntity<PatientDTO> patientResponse =  restTemplate.exchange(
                 "http://9001/users/patient/"+dg.getPatientId(), HttpMethod.GET, jwtEntity,
                 PatientDTO.class);

         PatientDTO patient = patientResponse.getBody();

         rt.setDataGlucose(dg);
         rt.setPatient(patient);
         return rt;
     }*/
    public List<DataGlucose> getDataGlucose() {
        return dataGlucoseRepository.findAll();
    }

    public DataGlucose getDataById(Long idGly) {
        return dataGlucoseRepository.findById(idGly).orElse(null);
    }

    public List<DataGlucose> getDataByKey(String key) {
        return dataGlucoseRepository.findDataGlucoseByDeviceKey(key);
    }

    public List<DataGlucose> getDataByDate(String key, String date) {
        return dataGlucoseRepository.findDataGlucoseByDeviceKeyAndDateEnregistrement(key, date);
    }


    public DataGlucose addNewValue(String key,
                                   int year,
                                   int month,
                                   int day,
                                   int hour,
                                   int munites,
                                   float glucose) {

        Calendar dateTimeInput = Calendar.getInstance();
        dateTimeInput.set(year, month, day, hour, munites, 0);
        month = month+1;
        String dateInput = String.valueOf(year)+'-'+month+'-'+day+'-'+hour+'-'+munites;

        // check if dateTimeInput exsists !
        if (!check(dateInput, key)) {

            return dataGlucoseRepository.save(
                    new DataGlucose(null,
                            dateTimeInput.getTime(),
                            dateInput,
                            glucose,
                            key));
        } else {
            return null;
        }
    }
    public boolean check(String datetime, String key){
        int i=0;
        List<DataGlucose> listData = getDataByKey(key);
        while (i<listData.size()) {
            String savedtime = listData.get(i).getDateEnregistrement();
            if (datetime.equals(savedtime)){
                return true;
            }else i++;
        }
        return false;
    }
}
