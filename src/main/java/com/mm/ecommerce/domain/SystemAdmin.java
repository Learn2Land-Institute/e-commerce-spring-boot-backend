package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.DBStatus;
import com.mm.ecommerce.enums.MerchantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class SystemAdmin extends User{

    @Enumerated(EnumType.STRING)
    private DBStatus consumerStatus = DBStatus.active;
}
