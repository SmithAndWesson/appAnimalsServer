package com.zoo.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Animal {

  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable=true)
  private String breed;
  @Column(nullable=true)
  private Long age;
  @Column(nullable = false)
  private String gender;
  @Column(nullable = false)
  private String owner_name;
  @Column(unique = true)
  private String owner_email;
}
