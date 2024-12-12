package com.mercadolibre.social.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.entity.Product;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.repository.IProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private List<Product> products = new ArrayList<>();

    public ProductRepository() throws IOException {
        products = loadDataBase();
    }

    private List<Product> loadDataBase() throws IOException {
        String FILE_PATH = "src/main/resources/products.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream inputStream = new FileInputStream(FILE_PATH)) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        }
    }
}
