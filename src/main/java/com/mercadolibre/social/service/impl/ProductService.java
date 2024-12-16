package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.dto.response.ProductCountPromoPostDto;
import com.mercadolibre.social.repository.IProductRepository;
import com.mercadolibre.social.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
