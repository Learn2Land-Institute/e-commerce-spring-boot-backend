package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.MerchantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends User{
    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;
    @OneToMany
    private List<Address> addressList;
    @OneToMany
    private List<PhoneNumber> phoneNumberList;
}
