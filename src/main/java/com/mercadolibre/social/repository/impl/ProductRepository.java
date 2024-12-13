package com.mercadolibre.social.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.entity.Product;
import com.mercadolibre.social.exception.NotFoundException;
import com.mercadolibre.social.repository.IProductRepository;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private List<Product> products;

    public ProductRepository() throws IOException {
        products = loadDataBase();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product findById(Integer id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
    }

    private List<Product> loadDataBase() throws IOException {
        String FILE_PATH = "src/main/resources/products.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream inputStream = new FileInputStream(FILE_PATH)) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        }
    }


}
