package com.zanonjonascodes.ssmts.unit_tests.user;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.zanonjonascodes.ssmts.fixture.UserFixture;
import com.zanonjonascodes.ssmts.user.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private UserFixture userFixture;

  @Value("${props.basic-auth.user}")
  private String basicAuthUser;

  @Value("${props.basic-auth.password}")
  private String basicAuthPassword;

  @BeforeEach
  private void setup() {
    userFixture = new UserFixture();
  }

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Test
	public void test_find_all() throws Exception {
		when(userService.findAll(any())).thenReturn(userFixture.getResponsePagedModel());
		
    this.mockMvc.perform(get("/user")
                          .with(SecurityMockMvcRequestPostProcessors.jwt())).andDo(print()).andExpect(status().isOk())
				                  .andExpect(content().string(containsString(userFixture.getResponseModel().getId())));
	}

  @Test
	public void test_find_by_id() throws Exception {
		when(userService.findById(userFixture.getEntity().getId())).thenReturn(userFixture.getResponseModel());
		
    this.mockMvc.perform(get("/user/{id}", userFixture.getEntity().getId())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(userFixture.getResponseModel().getEmail())));
	}

  @Test
	public void test_create() throws Exception {
		when(userService.create(userFixture.getRequestModel())).thenReturn(userFixture.getResponseModel());
    this.mockMvc.perform(post("/user")
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(userFixture.getRequestModelAsJson())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isCreated())
				                  .andExpect(content().string(containsString(userFixture.getResponseModel().getId())));
	}

  @Test
  public void delete_by_id() throws Exception {
    this.mockMvc.perform(delete("/user/{id}", userFixture.getEntity().getId())
        .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
	public void test_patch() throws Exception {
		when(userService.patch(userFixture.getRequestModelAsMap(), userFixture.getEntity().getId())).thenReturn(userFixture.getResponseModel());
    this.mockMvc.perform(patch("/user/{id}", userFixture.getEntity().getId())
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(userFixture.getRequestModelAsJson())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(userFixture.getResponseModel().getId())));
	}

}
