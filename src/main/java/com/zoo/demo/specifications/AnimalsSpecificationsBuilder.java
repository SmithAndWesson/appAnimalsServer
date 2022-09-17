package com.zoo.demo.specifications;

import com.zoo.demo.entity.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;

public class AnimalsSpecificationsBuilder {

  private final List<SearchCriteria> params;

  public AnimalsSpecificationsBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public AnimalsSpecificationsBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Animal> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification> specs = params.stream()
        .map(AnimalSpecification::new)
        .collect(Collectors.toList());

    Specification result = specs.get(0);

    for (int i = 1; i < params.size(); i++) {
      result = params.get(i)
          .isOrPredicate()
          ? Specification.where(result)
          .or(specs.get(i))
          : Specification.where(result)
              .and(specs.get(i));
    }
    return result;
  }

}
