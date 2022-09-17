package com.zoo.demo.facade;

import com.zoo.demo.dto.AnimalDTO;
import com.zoo.demo.entity.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalFacade {

  public AnimalDTO animalToAnimalDTO(Animal animal){

    AnimalDTO animalDTO = new AnimalDTO();
    animalDTO.setId(animal.getId());
    animalDTO.setName(animal.getName());
    animalDTO.setBreed(animal.getBreed());
    animalDTO.setAge(animal.getAge());
    animalDTO.setGender(animal.getGender());
    animalDTO.setOwner_name(animal.getOwner_name());
    animalDTO.setOwner_email(animal.getOwner_email());

    return animalDTO;
  }

}
