package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.State;
import com.mm.ecommerce.enums.AddressType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import javax.xml.transform.sax.SAXResult;

@Data
public class AddressDTO {
    private AddressType addressType;
    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    //State
    private Integer stateId;

}
