package com.zanonjonascodes.ssmts.tenant;

import com.zanonjonascodes.ssmts.core.rest.crud.ResponseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class TenantResponseModel extends ResponseModel<TenantResponseModel, String> {
  protected String companyName;

  protected String logoImgUrl;
}
