package com.zanonjonascodes.ssmts.unit_tests.role;

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
import com.zanonjonascodes.ssmts.fixture.RoleFixture;
import com.zanonjonascodes.ssmts.role.RoleEntity;
import com.zanonjonascodes.ssmts.role.RoleMapper;
import com.zanonjonascodes.ssmts.role.RoleMapperImpl;
import com.zanonjonascodes.ssmts.role.RoleModelAssembler;
import com.zanonjonascodes.ssmts.role.RoleRepository;
import com.zanonjonascodes.ssmts.role.RoleResponseModel;
import com.zanonjonascodes.ssmts.role.RoleService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

  @Mock
  private RoleRepository repository;

  @InjectMocks
  private RoleService roleService;

  private RoleFixture fixture;

  RoleEntity tRoleEntity;

  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);

    JacksonConfig jacksonConfig = new JacksonConfig();
    objectMapper = jacksonConfig.getObjectMapper();

    RoleMapper mapper = new RoleMapperImpl();
    RoleModelAssembler roleModelAssembler = new RoleModelAssembler(mapper);
    PagedResourcesAssembler<RoleEntity> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
    roleService = new RoleService(objectMapper, repository, mapper, roleModelAssembler, pagedResourcesAssembler);

    fixture = new RoleFixture();
    tRoleEntity = fixture.getEntity();

  }

  @AfterEach
  public void terdown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void test_find_all() {
    Pageable pageable = PageRequest.of(0, 10);
    given(repository.findAll(pageable)).willReturn(fixture.getEntityPage());

    PagedModel<RoleResponseModel> pagedModel = roleService.findAll(pageable);

    int i = 0;
    for (RoleResponseModel responseModel : pagedModel) {
      assertEquals(fixture.getEntityList().get(i).getId(), responseModel.getId());
      i = +1;
    }
  }

  @Test
  void test_find_by_id() {
    given(repository.findById(tRoleEntity.getId())).willReturn(Optional.of(tRoleEntity));
    RoleResponseModel responseModel = roleService.findById(tRoleEntity.getId());
    assertEquals(tRoleEntity.getId(), responseModel.getId());
  }

  @Test
  void test_create() {
    given(repository.save(tRoleEntity)).willReturn(tRoleEntity);
    RoleResponseModel responseModel = roleService.create(fixture.getRequestModel());
    assertEquals(tRoleEntity.getId(), responseModel.getId());
  }

  @Test
  void test_delete() {
    assertDoesNotThrow(() -> roleService.delete(tRoleEntity.getId()));
  }

}
