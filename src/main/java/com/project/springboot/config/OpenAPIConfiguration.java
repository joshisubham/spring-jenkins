package com.project.springboot.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.springboot.model.Product;
import com.project.springboot.repository.ProductRepository;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

   @Bean
   public OpenAPI defineOpenApi() {
       Server server = new Server();
       server.setUrl("http://localhost:8080");
       server.setDescription("Development");

       Contact myContact = new Contact();
       myContact.setName("Subham Joshi");
       myContact.setEmail("subham.joshi@gmail.com");

       Info information = new Info()
               .title("Product API")
               .version("1.0")
               .description("This API exposes endpoints to manage employees.")
               .termsOfService("http://swagger.io/terms/")
               .contact(myContact)
       		   .license(new License().name("Apache 2.0").url("http://springdoc.org"));
       return new OpenAPI().info(information).servers(List.of(server));
   }
   @Bean
   CommandLineRunner loadData(ProductRepository repository) {
       return args -> {
           repository.save(new Product("Product1"));
           repository.save(new Product("Product2"));
           repository.save(new Product("Product3"));
       };
   }
}