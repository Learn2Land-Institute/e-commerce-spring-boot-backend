package com.mm.ecommerce.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    @ManyToOne
    @JoinColumn(name = "parent_product_id")
    private Product parentProduct;

    @OneToMany(mappedBy = "parentProduct", cascade = CascadeType.ALL)
    private List<Product> subProduct = new ArrayList<>();

    //@OneToOne
    //private ProductDetail productDetail;
    public void addSubProduct(Product subProduct) {
        subProduct.setParentProduct(this);
        this.subProduct.add(subProduct);
    }

    public void removeSubProduct(Product subProduct) {
        subProduct.setParentProduct(null);
        this.subProduct.remove(subProduct);
    }

    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    public List<Product> getSubProduct() {
        return subProduct;
    }

    public void setSubProduct(List<Product> subProduct) {
        this.subProduct = subProduct;
    }
}
