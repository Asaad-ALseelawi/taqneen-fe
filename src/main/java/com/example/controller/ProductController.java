package com.example.controller;

import com.example.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import org.springframework.aot.hint.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Controller
public class ProductController implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;


    private final RestTemplate restTemplate;

    @Autowired
    public ProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Product> getProducts() {
        String url = "http://localhost:8081/api/v1/products/";
        ResponseEntity<List<Product>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        System.out.println("RESULTS :: " + result);
        if (result.getStatusCode() == HttpStatus.OK) {

            return ResponseEntity.ok(result.getBody()).getBody();
        } else return Collections.emptyList();
    }



    public void deleteProductByID(int productId) {
        String url = "http://localhost:8081/api/v1/products/" + productId;
        ResponseEntity<Void> result = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class);
        if (result.getStatusCode().is2xxSuccessful()) {
            System.out.println("Product with ID " + productId + " deleted successfully.");
        } else {
            System.out.println("Failed to delete product with ID " + productId + ". Status code: " + result.getStatusCode());
        }


    }

    public Product addProduct(Product product) {
        String url = "http://localhost:8081/api/v1/product";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);
        ResponseEntity<Product> result = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Product>() {
                });

        System.out.println("RESULT :: " + result);
        return ResponseEntity.ok(result.getBody()).getBody();
    }

    public Product updateProduct(Product product) {
        String url = "http://localhost:8081/api/v1/products";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);


        ResponseEntity<Product> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Product.class
        );


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Product updated successfully: " + responseEntity.getBody());
        } else {

            System.err.println("Failed to update product: " + responseEntity.getStatusCode());
        }
        return product;
    }
}
