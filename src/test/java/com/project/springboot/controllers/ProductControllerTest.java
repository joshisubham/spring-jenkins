package com.project.springboot.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springboot.model.Product;
import com.project.springboot.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product(1L, "Product 1");
        Product product2 = new Product(2L, "Product 2");
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }
    @Test
    public void testGetProductByIdFound() throws Exception {
        Product product = new Product(1L, "Product 1");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }
    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
   /* @Test
    public void testCreateProduct() throws Exception {
        // Create a Product object with only the name (no ID)
        Product product = new Product("Product 3");

        // Create a Product object with an ID for the expected result
        Product savedProduct = new Product(1L, "Product 3");

        // Mock the repository to return the saved product
        when(productRepository.save(product)).thenReturn(savedProduct);

        // Perform the POST request and capture the result
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andDo(print()) // Print the request and response details
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check content type
                .andReturn();

        // Print the response body to debug
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }*/
    @Test
    public void testDeleteProductFound() throws Exception {
        when(productRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        when(productRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNotFound());
    }
}