package com.zoo.demo.specifications;

import com.zoo.demo.entity.Animal;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AnimalSpecification implements Specification<Animal> {

  private SearchCriteria criteria;

  @Override
  public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return criteriaBuilder.greaterThanOrEqualTo(
          root.<String> get(criteria.getKey()), criteria.getValue().toString());
    }
    else if (criteria.getOperation().equalsIgnoreCase("<")) {
      return criteriaBuilder.lessThanOrEqualTo(
          root.<String> get(criteria.getKey()), criteria.getValue().toString());
    }
    else if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return criteriaBuilder.like(
            root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
      } else {
        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }
    return null;
  }
}
