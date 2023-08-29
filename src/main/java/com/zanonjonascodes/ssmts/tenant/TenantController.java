package com.zanonjonascodes.ssmts.tenant;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanonjonascodes.ssmts.core.rest.crud.CrudControllerAbstract;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudService;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/tenant")
@AllArgsConstructor
public class TenantController
    extends CrudControllerAbstract<TenantEntity, String, TenantRequestModel, TenantResponseModel> {

  private TenantService tenantService;

  @Override
  public CrudService<TenantEntity, String, TenantRequestModel, TenantResponseModel> getCrudService() {
    return tenantService;
  }

  @GetMapping
  @Override
  @Secured({"ADMIN"})
  public ResponseEntity<PagedModel<TenantResponseModel>> findAll(Pageable pageable) {
    return super.findAll(pageable);
  }

  

}
