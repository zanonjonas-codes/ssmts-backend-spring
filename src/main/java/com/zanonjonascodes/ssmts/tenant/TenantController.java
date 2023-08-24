package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;

@RestController()
@RequestMapping("/tenant")
public class TenantController {
  @Autowired
  private TenantService tenantService;

  @Autowired
  private TenantMapper tenantMapper;

  @Autowired
  private TenantModelAssembler tenantModelAssembler;

  @Autowired
  private PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler;

  // @PreAuthorize("hasRole('USER')")
  @PostMapping()
  public ResponseEntity<TenantResponseModel> create(
      @RequestBody TenantRequestModel requestModel) {

    TenantEntity tenantEntity = tenantService.create(requestModel);
    return new ResponseEntity<TenantResponseModel>(tenantMapper.toResponse(tenantEntity), HttpStatus.OK);

  }

  @PatchMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> patch(
      @RequestBody Map<String, Object> requestModel, @PathVariable UUID uuid)
      throws JsonProcessingException, JsonPatchException {

    TenantEntity tenantEntity = tenantService.patch(requestModel, uuid);
    return new ResponseEntity<TenantResponseModel>(tenantMapper.toResponse(tenantEntity), HttpStatus.OK);
  }

  @GetMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> findById(@PathVariable UUID uuid) {
    TenantEntity tenantEntity = tenantService.findById(uuid);
    TenantResponseModel responseModel = tenantModelAssembler.toModel(tenantEntity);
    return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<PagedModel<TenantResponseModel>> findAll(Pageable pageable) {

    Page<TenantEntity> page = tenantService.findAll(pageable);
    PagedModel<TenantResponseModel> responseModel = pagedResourcesAssembler.toModel(page, tenantModelAssembler);
    return new ResponseEntity<>(responseModel, HttpStatus.OK);

  }

  @DeleteMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> delete(@PathVariable UUID uuid) {
    tenantService.delete(uuid);
    return new ResponseEntity<TenantResponseModel>(HttpStatus.OK);
  }
}
