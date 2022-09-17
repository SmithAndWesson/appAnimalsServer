package com.zoo.demo.dto;


import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimalDTO {

  private Long id;
  @NotEmpty
  private String name;
  @NotEmpty
  private String breed;
  private Long age;
  private String gender;
  private String owner_name;
  @NotEmpty
  private String owner_email;
}

