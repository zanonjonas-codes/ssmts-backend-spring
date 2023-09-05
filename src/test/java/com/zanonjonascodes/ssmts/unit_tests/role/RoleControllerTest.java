package com.zanonjonascodes.ssmts.unit_tests.role;

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

import com.zanonjonascodes.ssmts.fixture.RoleFixture;
import com.zanonjonascodes.ssmts.role.RoleService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoleService roleService;

  private RoleFixture roleFixture;

  @Value("${props.basic-auth.user}")
  private String basicAuthUser;

  @Value("${props.basic-auth.password}")
  private String basicAuthPassword;

  @BeforeEach
  private void setup() {
    roleFixture = new RoleFixture();
  }

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Test
	public void test_find_all() throws Exception {
		when(roleService.findAll(any())).thenReturn(roleFixture.getResponsePagedModel());
		
    this.mockMvc.perform(get("/role")
                          .with(SecurityMockMvcRequestPostProcessors.jwt())).andDo(print()).andExpect(status().isOk())
				                  .andExpect(content().string(containsString(roleFixture.getResponseModel().getId())));
	}

  @Test
	public void test_find_by_id() throws Exception {
		when(roleService.findById(roleFixture.getEntity().getId())).thenReturn(roleFixture.getResponseModel());
		
    this.mockMvc.perform(get("/role/{id}", roleFixture.getEntity().getId())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(roleFixture.getResponseModel().getId())));
	}

  @Test
	public void test_create() throws Exception {
		when(roleService.create(roleFixture.getRequestModel())).thenReturn(roleFixture.getResponseModel());
    this.mockMvc.perform(post("/role")
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(roleFixture.getRequestModelAsJson())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isCreated())
				                  .andExpect(content().string(containsString(roleFixture.getResponseModel().getId())));
	}

  @Test
  public void delete_by_id() throws Exception {
    this.mockMvc.perform(delete("/role/{id}", roleFixture.getEntity().getId())
        .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
	public void test_patch() throws Exception {
		when(roleService.patch(roleFixture.getRequestModelAsMap(), roleFixture.getEntity().getId())).thenReturn(roleFixture.getResponseModel());
    this.mockMvc.perform(patch("/role/{id}", roleFixture.getEntity().getId())
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(roleFixture.getRequestModelAsJson())
                          .with(SecurityMockMvcRequestPostProcessors.jwt()))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(roleFixture.getResponseModel().getId())));
	}

}
