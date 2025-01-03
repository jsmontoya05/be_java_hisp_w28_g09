package com.mercadolibre.social.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer id;
    private Integer userId;
    private LocalDate date;
    private Integer productId;
    private Integer category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;
}
