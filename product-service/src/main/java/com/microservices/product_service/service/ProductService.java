package com.microservices.product_service.service;

import com.microservices.product_service.dto.ProductRequest;
import com.microservices.product_service.dto.ProductRespose;
import com.microservices.product_service.model.Product;
import com.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public ProductRespose createProduct(ProductRequest productRequest){
        Product product=new Product(productRequest.id(), productRequest.name(), productRequest.description(), productRequest.price());

        productRepository.save(product);

        return new ProductRespose(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductRespose> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductRespose(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
