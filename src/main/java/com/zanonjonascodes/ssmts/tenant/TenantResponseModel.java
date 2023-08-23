package com.zanonjonascodes.ssmts.tenant;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class TenantResponseModel extends RepresentationModel<TenantResponseModel> {
  protected String id;

  protected String companyName;

  protected String logoImgUrl;
}
