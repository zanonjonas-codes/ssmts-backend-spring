package com.zanonjonascodes.ssmts.tenant;

import com.zanonjonascodes.ssmts.core.rest.crud.RequestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantRequestModel implements RequestModel {
  protected String companyName;

  protected String logoImgUrl;
}
