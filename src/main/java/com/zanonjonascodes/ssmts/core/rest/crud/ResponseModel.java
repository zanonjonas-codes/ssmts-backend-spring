package com.zanonjonascodes.ssmts.core.rest.crud;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class ResponseModel<R extends RepresentationModel<R>, I> extends RepresentationModel<R> {
  
  private I id;

  private Integer version;

  OffsetDateTime createdAt;

  OffsetDateTime updatedAt;

}
