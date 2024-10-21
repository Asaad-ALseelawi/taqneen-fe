package com.example.beans;

import com.example.controller.ProductController;
import com.example.model.Product;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ProductBean implements Serializable {
    private List<Product> products = new ArrayList<>();
    private Product newProduct = new Product();
    private Product selectedProduct;

    @Autowired
    private ProductController productController;

    public ProductBean() {
    }

    public List<Product> getProducts() {
        return productController.getProducts();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void cancel() {
         selectedProduct=null;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }


    public void addProduct() {
        productController.addProduct(newProduct);
        newProduct = new Product();
    }


    public void updateProduct() {
        productController.updateProduct(selectedProduct);
        selectedProduct = null;
    }


    public void deleteProduct(Product product) {
        productController.deleteProductByID(product.getId()); // Assuming deleteProduct method exists in ProductController
    }


    public void selectProduct(Product product) {
        this.selectedProduct = product;
    }
}
