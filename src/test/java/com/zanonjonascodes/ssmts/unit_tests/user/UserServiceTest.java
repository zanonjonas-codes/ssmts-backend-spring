package com.zanonjonascodes.ssmts.unit_tests.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanonjonascodes.ssmts.core.config.parser.JacksonConfig;
import com.zanonjonascodes.ssmts.core.config.security.SecurityConfig;
import com.zanonjonascodes.ssmts.fixture.UserFixture;
import com.zanonjonascodes.ssmts.user.UserEntity;
import com.zanonjonascodes.ssmts.user.UserMapper;
import com.zanonjonascodes.ssmts.user.UserMapperImpl;
import com.zanonjonascodes.ssmts.user.UserModelAssembler;
import com.zanonjonascodes.ssmts.user.UserRepository;
import com.zanonjonascodes.ssmts.user.UserResponseModel;
import com.zanonjonascodes.ssmts.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserService userService;

  private UserFixture fixture;

  UserEntity tUserEntity;

  ObjectMapper objectMapper;

  @BeforeEach
  @MockitoSettings(strictness = Strictness.LENIENT)
  public void setup() {
    HttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(servletRequestAttributes);

    JacksonConfig jacksonConfig = new JacksonConfig();
    objectMapper = jacksonConfig.getObjectMapper();

    UserMapper mapper = new UserMapperImpl();
    UserModelAssembler userModelAssembler = new UserModelAssembler(mapper);
    PagedResourcesAssembler<UserEntity> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
    BCryptPasswordEncoder passwordEncoder = new SecurityConfig().passwordEncoder();

    userService = new UserService(objectMapper, repository, mapper, userModelAssembler, pagedResourcesAssembler, passwordEncoder);

    fixture = new UserFixture();
    tUserEntity = fixture.getEntity();

  }

  @AfterEach
  public void terdown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void test_find_all() {
    Pageable pageable = PageRequest.of(0, 10);
    given(repository.findAll(pageable)).willReturn(fixture.getEntityPage());

    PagedModel<UserResponseModel> pagedModel = userService.findAll(pageable);

    int i = 0;
    for (UserResponseModel responseModel : pagedModel) {
      assertEquals(fixture.getEntityList().get(i).getId(), responseModel.getId());
      i = +1;
    }
  }

  @Test
  void test_find_by_id() {
    given(repository.findById(tUserEntity.getId())).willReturn(Optional.of(tUserEntity));
    UserResponseModel responseModel = userService.findById(tUserEntity.getId());
    assertEquals(tUserEntity.getId(), responseModel.getId());
  }

  @Test
  void test_create() {
    given(repository.save(any())).willReturn(tUserEntity);
    System.out.println(tUserEntity.getPassword());
    UserResponseModel responseModel = userService.create(fixture.getRequestModel());
    assertEquals(tUserEntity.getId(), responseModel.getId());
  }

  @Test
  void test_delete() {
    assertDoesNotThrow(() -> userService.delete(tUserEntity.getId()));
  }
}
