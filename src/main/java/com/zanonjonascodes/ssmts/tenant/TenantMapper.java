package com.zanonjonascodes.ssmts.tenant;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
  TenantEntity toEntity(TenantRequestModel requestModel);
  
  TenantResponseModel toResponse(TenantEntity entity);

  Iterable<TenantResponseModel> toResponse(Iterable<TenantEntity> entity);
}
