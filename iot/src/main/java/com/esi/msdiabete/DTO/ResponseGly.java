package com.esi.msdiabete.DTO;

import com.esi.msdiabete.entities.DataGlucose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseGly {
    DataGlucose dataGlucose;
    Long idPatient;
}
