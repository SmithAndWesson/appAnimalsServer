package com.zoo.demo.repository;

import com.zoo.demo.entity.Animal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

  Optional<Animal> findAnimalByName(String name);
  Optional<Animal> findAnimalById(Long id);
  List<Animal> findAllByOrderById();

}
