package com.zoo.demo.service;

import com.zoo.demo.dto.AnimalDTO;
import com.zoo.demo.entity.Animal;
import com.zoo.demo.exceptions.AnimalNotFound;
import com.zoo.demo.repository.AnimalRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
  public static final Logger LOGGER = LoggerFactory.getLogger(
      AnimalService.class);

 private final AnimalRepository animalRepository;

 @Autowired
  public AnimalService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  public Animal createAnimal(AnimalDTO animalDTO){
   Animal animal = new Animal();
    animal.setAge(animalDTO.getAge());
    animal.setBreed(animalDTO.getBreed());
    animal.setGender(animalDTO.getGender());
    animal.setName(animalDTO.getName());
    animal.setOwner_email(animalDTO.getOwner_email());
    animal.setOwner_name(animalDTO.getOwner_name());
    return animalRepository.save(animal);
  }

  public Page<Animal> getAllAnimals(Specification<Animal> specification, Pageable paging) {
   Page<Animal> pagedResult = animalRepository.findAll(specification, paging);

   return pagedResult;
  }

 public Animal getAnimalById(Long id) {
  return animalRepository.findById(id).orElseThrow(() -> new AnimalNotFound("Animal can not be found for "));
 }

 public void deleteAnimal(Long animalId){
  Animal animal = getAnimalById(animalId);
  animalRepository.delete(animal);
 }
}
