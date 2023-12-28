package com.mm.ecommerce.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;
    private Double price;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "limitation_id")
    private ProductLimitation productLimitation;

    @ManyToOne
    @JoinColumn(name = "parent_product_id")
    private Product parentProduct;

    @OneToMany(mappedBy = "parentProduct", cascade = CascadeType.ALL)
    private List<Product> subProduct = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "details_id")
    private ProductDetail productDetail;

    @OneToMany
    private List<ProductReview> productReviewList;

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
