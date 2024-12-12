package com.mercadolibre.social.repository.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mercadolibre.social.entity.Post;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.repository.IPostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository implements IPostRepository {

    private List<Post> posts;

    public PostRepository() throws IOException {
        posts = loadDataBase();
    }

    private List<Post> loadDataBase() throws IOException {
        String FILE_PATH = "src/main/resources/posts.json";
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext text) throws IOException {
                String date = p.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return LocalDate.parse(date, formatter);
            }
        });

        objectMapper.registerModule(module);

        try (FileInputStream inputStream = new FileInputStream(FILE_PATH)) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Post>>() {});
        }
    }
}
