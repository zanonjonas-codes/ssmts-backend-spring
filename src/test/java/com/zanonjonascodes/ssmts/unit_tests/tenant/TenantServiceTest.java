package com.zanonjonascodes.ssmts.unit_tests.tenant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanonjonascodes.ssmts.core.config.parser.JacksonConfig;
import com.zanonjonascodes.ssmts.fixture.TenantFixture;
import com.zanonjonascodes.ssmts.tenant.TenantEntity;
import com.zanonjonascodes.ssmts.tenant.TenantMapper;
import com.zanonjonascodes.ssmts.tenant.TenantMapperImpl;
import com.zanonjonascodes.ssmts.tenant.TenantModelAssembler;
import com.zanonjonascodes.ssmts.tenant.TenantRepository;
import com.zanonjonascodes.ssmts.tenant.TenantResponseModel;
import com.zanonjonascodes.ssmts.tenant.TenantService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {

  @Mock
  private TenantRepository repository;

  @InjectMocks
  private TenantService tenantService;

  private TenantFixture fixture;

  TenantEntity tTenantEntity;

  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);

    JacksonConfig jacksonConfig = new JacksonConfig();
    objectMapper = jacksonConfig.getObjectMapper();

    TenantMapper mapper = new TenantMapperImpl();
    TenantModelAssembler tenantModelAssembler = new TenantModelAssembler(mapper);
    PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
    tenantService = new TenantService(objectMapper, repository, mapper, tenantModelAssembler, pagedResourcesAssembler);

    fixture = new TenantFixture();
    tTenantEntity = fixture.getEntity();

  }

  @AfterEach
  public void terdown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void test_find_all() {
    Pageable pageable = PageRequest.of(0, 10);
    given(repository.findAll(pageable)).willReturn(fixture.getEntityPage());

    PagedModel<TenantResponseModel> pagedModel = tenantService.findAll(pageable);

    int i = 0;
    for (TenantResponseModel responseModel : pagedModel) {
      assertEquals(fixture.getEntityList().get(i).getId(), responseModel.getId());
      i = +1;
    }
  }

  @Test
  void test_find_by_id() {
    given(repository.findById(tTenantEntity.getId())).willReturn(Optional.of(tTenantEntity));
    TenantResponseModel responseModel = tenantService.findById(tTenantEntity.getId());
    assertEquals(tTenantEntity.getId(), responseModel.getId());
  }

  @Test
  void test_create() {
    given(repository.save(tTenantEntity)).willReturn(tTenantEntity);
    TenantResponseModel responseModel = tenantService.create(fixture.getRequestModel());
    assertEquals(tTenantEntity.getId(), responseModel.getId());
  }

  @Test
  void test_delete() {
    assertDoesNotThrow(() -> tenantService.delete(tTenantEntity.getId()));
  }

}
