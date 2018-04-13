package com.spand.bridgecom.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
public abstract class IdEntity {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

}
