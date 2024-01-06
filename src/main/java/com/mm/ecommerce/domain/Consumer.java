package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
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
public class Consumer extends User{
    @OneToMany
    private List<Address> addressList;
    @OneToMany
    private List<PhoneNumber> phoneNumberList;
}
