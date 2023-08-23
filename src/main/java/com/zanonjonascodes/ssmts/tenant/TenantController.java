package com.zanonjonascodes.ssmts.tenant;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("tenant")
public class TenantController {
  @Autowired
  TenantService tenantService;

  @Autowired
  TenantMapper tenantMapper;

  @Autowired
  TenantModelAssembler tenantModelAssembler;

  // @PreAuthorize("hasRole('USER')")
  @PostMapping()
  public ResponseEntity<TenantResponseModel> create(
      @RequestBody TenantRequestModel requestModel) {
    try {
      TenantEntity tenantEntity = tenantService.create(requestModel);
      return new ResponseEntity<TenantResponseModel>(tenantMapper.toResponse(tenantEntity), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> patch(
      @RequestBody Map<String, Object> requestModel, @PathVariable UUID uuid) {
    try {
      TenantEntity tenantEntity = tenantService.patch(requestModel, uuid);
      return new ResponseEntity<TenantResponseModel>(tenantMapper.toResponse(tenantEntity), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{uuid}")
  public ResponseEntity<TenantResponseModel> findById(@PathVariable UUID uuid) {
    try {
      TenantEntity tenantEntity = tenantService.findById(uuid);
      TenantResponseModel responseModel = tenantModelAssembler.toModel(tenantEntity);
      return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<TenantResponseModel> findAll() {
    try {
      TenantEntity tenantEntity = tenantService.findAll();
      TenantResponseModel responseModel = tenantModelAssembler.toModel(tenantEntity);
      return new ResponseEntity<TenantResponseModel>(responseModel, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
