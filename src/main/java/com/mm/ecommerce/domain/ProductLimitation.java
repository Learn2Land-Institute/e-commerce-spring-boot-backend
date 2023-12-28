package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.Frequency;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;

@Entity
public class ProductLimitation {
    @Id
    @GeneratedValue(generator = "shared_generator")
    @GenericGenerator(name = "shared_generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "product"))
    private String id;

    private Integer LimitQtyPerUser;
    private Integer limitFrequencyValue;
    private Frequency limitFrequency;
    private LocalDateTime LimitStartDateTime;
    private LocalDateTime LimitEndDateTime;
}
