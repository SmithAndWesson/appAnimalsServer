package com.zoo.demo.web;

import com.zoo.demo.dto.AnimalDTO;
import com.zoo.demo.entity.Animal;
import com.zoo.demo.facade.AnimalFacade;
import com.zoo.demo.response.MessageResponse;
import com.zoo.demo.service.AnimalService;
import com.zoo.demo.specifications.AnimalSpecification;
import com.zoo.demo.specifications.AnimalsSpecificationsBuilder;
import com.zoo.demo.validations.ResponseErrorValidation;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin
public class AnimalCntroller {

  @Autowired
  private AnimalService animalService;
  @Autowired
  private AnimalFacade animalFacade;
  @Autowired
  private ResponseErrorValidation responseErrorValidation;

  @PostMapping("/create")
  public ResponseEntity<Object> createPost(@Valid @RequestBody AnimalDTO animalDTO,
      BindingResult bindingResult) {

    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
    if (!ObjectUtils.isEmpty(errors)) {
      return errors;
    }

    Animal animal = animalService.createAnimal(animalDTO);
    AnimalDTO createdAnimalDTO = animalFacade.animalToAnimalDTO(animal);

    return new ResponseEntity<>(createdAnimalDTO, HttpStatus.OK);
  }

 @GetMapping("/all")
  public ResponseEntity<List<AnimalDTO>> getAllAnimals(
      @RequestParam(defaultValue = "filter") String filter,
      @RequestParam(defaultValue = "0") int _start,
      @RequestParam(defaultValue = "10") int _end,
      @RequestParam(defaultValue = "name") String _sort,
     @RequestParam(defaultValue = "asc") String _order) {

    AnimalsSpecificationsBuilder builder = new AnimalsSpecificationsBuilder();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(filter + ",");
    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
   int size = _end - _start;
   int page = (_end/size) - 1;

    Specification<Animal> spec = builder.build();

    Pageable paging = PageRequest.of(page, size, Sort.by(
        _order.equals("desc") ? Direction.DESC : Direction.ASC,_sort));

    List<AnimalDTO> AnimalDTOList = animalService.getAllAnimals(spec, paging)
          .stream()
          .map(animalFacade::animalToAnimalDTO)//animal -> animalFacade.animalToAnimalDTO(animal)
          .collect(Collectors.toList());



    return new ResponseEntity<>(AnimalDTOList, HttpStatus.OK);
  }

  @PostMapping("/{animalId}/delete")
  public ResponseEntity<MessageResponse> deletePost(@PathVariable("animalId") String postId) {
    animalService.deleteAnimal(Long.parseLong(postId));
    return new ResponseEntity<>(new MessageResponse("Post was deleted."), HttpStatus.OK);
  }

}
