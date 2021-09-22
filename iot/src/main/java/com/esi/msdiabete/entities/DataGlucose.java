package com.esi.msdiabete.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class DataGlucose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idData;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeEnregistrement;

    private String dateEnregistrement; // date only --> order by date

    private float value;
    private String deviceKey;
}
