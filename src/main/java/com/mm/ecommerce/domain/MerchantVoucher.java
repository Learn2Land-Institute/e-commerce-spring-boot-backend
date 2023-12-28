package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class MerchantVoucher extends Voucher{
    @OneToMany
    List<Category> categoryList;
    @OneToMany
    List<Product> productList;


}
