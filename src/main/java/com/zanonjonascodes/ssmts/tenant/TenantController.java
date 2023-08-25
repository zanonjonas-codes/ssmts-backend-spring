package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;

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

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/tenant")
@AllArgsConstructor
public class TenantController
    implements CrudController<TenantEntity, String, TenantRequestModel, TenantResponseModel> {

  private TenantService tenantService;

  @PatchMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> patch(
      @RequestBody Map<String, Object> requestModel, @PathVariable String uuid)
      throws JsonProcessingException, JsonPatchException {

    TenantResponseModel responseModel = tenantService.patch(requestModel, uuid);
    return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
  }

  @GetMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> findById(@PathVariable String uuid) {
    TenantResponseModel responseModel = tenantService.findById(uuid);
    return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<PagedModel<TenantResponseModel>> findAll(Pageable pageable) {

    PagedModel<TenantResponseModel> responseModel = tenantService.findAll(pageable);
    return new ResponseEntity<>(responseModel, HttpStatus.OK);

  }

  @DeleteMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> delete(@PathVariable String uuid) {
    tenantService.delete(uuid);
    return new ResponseEntity<TenantResponseModel>(HttpStatus.OK);
  }
}
