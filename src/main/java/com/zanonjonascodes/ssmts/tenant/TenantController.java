package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudController;
import com.zanonjonascodes.ssmts.core.rest.crud.CrudService;

@RestController()
@RequestMapping("/tenant")
public class TenantController
    implements CrudController<TenantEntity, UUID, TenantRequestModel, TenantResponseModel> {
  @Autowired
  private TenantService tenantService;

  @Override
  public CrudService<TenantEntity, UUID, TenantRequestModel, TenantResponseModel> getService() {
    return tenantService;
  }

  @PatchMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> patch(
      @RequestBody Map<String, Object> requestModel, @PathVariable UUID uuid)
      throws JsonProcessingException, JsonPatchException {

    TenantResponseModel responseModel = tenantService.patch(requestModel, uuid);
    return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
  }

  @GetMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> findById(@PathVariable UUID uuid) {
    TenantResponseModel responseModel = tenantService.findById(uuid);
    return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<PagedModel<TenantResponseModel>> findAll(Pageable pageable) {

    PagedModel<TenantResponseModel> responseModel = tenantService.findAll(pageable);
    return new ResponseEntity<>(responseModel, HttpStatus.OK);

  }

  @DeleteMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> delete(@PathVariable UUID uuid) {
    tenantService.delete(uuid);
    return new ResponseEntity<TenantResponseModel>(HttpStatus.OK);
  }
}
