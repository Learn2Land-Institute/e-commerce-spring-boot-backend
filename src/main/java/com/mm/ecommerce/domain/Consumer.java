package com.mm.ecommerce.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consumer extends User{
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Address> addressList;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PhoneNumber> phoneNumberList;
}
