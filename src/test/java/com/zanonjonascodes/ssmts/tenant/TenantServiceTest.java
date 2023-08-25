package com.zanonjonascodes.ssmts.tenant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zanonjonascodes.ssmts.fixture.TenantFixture;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {

  @Configuration
  static class Config {
  }

  @Mock
  private TenantRepository repository;

  @InjectMocks
  private TenantService tenantService;

  private TenantFixture fixture;

  @BeforeEach
  public void setup() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);

    TenantMapper mapper = new TenantMapperImpl();
    TenantModelAssembler tenantModelAssembler = new TenantModelAssembler(mapper);
    PagedResourcesAssembler<TenantEntity> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
    tenantService = new TenantService(repository, mapper, tenantModelAssembler, pagedResourcesAssembler);

    fixture = new TenantFixture();
  }

  @AfterEach
  public void terdown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void testFindAll() {
    Pageable pageable = PageRequest.of(0, 10);
    given(repository.findAll(pageable)).willReturn(fixture.getEntityPage());
    
    PagedModel<TenantResponseModel> pagedModel = tenantService.findAll(pageable);
    
    int i = 0;
    for (TenantResponseModel responseModel : pagedModel) {
      assertEquals(fixture.getEntityList().get(i).getId(), responseModel.getId());
      i =+ 1;
    }
  }
}
