package com.project.springboot.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Employee {
   @Id
   @NotNull
   private int id;

   @NotNull
   @Size(min = 1, max = 20)
   private String firstName;

   @NotNull
   @Size(min = 1, max = 50)
   private String lastName;
}