package com.zanonjonascodes.ssmts.tenant;

import com.zanonjonascodes.ssmts.core.rest.crud.RequestModel;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TenantRequestModel implements RequestModel {
  protected String companyName;

  protected String logoImgUrl;
}
