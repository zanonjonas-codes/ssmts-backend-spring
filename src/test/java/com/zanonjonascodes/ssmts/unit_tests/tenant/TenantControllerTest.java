package com.zanonjonascodes.ssmts.unit_tests.tenant;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.zanonjonascodes.ssmts.fixture.TenantFixture;
import com.zanonjonascodes.ssmts.tenant.TenantService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TenantControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TenantService tenantService;

  private TenantFixture tenantFixture;

  @Value("${props.basic-auth.user}")
  private String basicAuthUser;

  @Value("${props.basic-auth.password}")
  private String basicAuthPassword;

  @BeforeEach
  private void setup() {
    tenantFixture = new TenantFixture();
  }

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Test
	public void test_find_all() throws Exception {
		when(tenantService.findAll(any())).thenReturn(tenantFixture.getResponsePagedModel());
		
    this.mockMvc.perform(get("/tenant")
                          .with(httpBasic(basicAuthUser, basicAuthPassword))).andDo(print()).andExpect(status().isOk())
				                  .andExpect(content().string(containsString(tenantFixture.getResponseModel().getTenantName())));
	}

  @Test
	public void test_find_by_id() throws Exception {
		when(tenantService.findById(tenantFixture.getEntity().getId())).thenReturn(tenantFixture.getResponseModel());
		
    this.mockMvc.perform(get("/tenant/{id}", tenantFixture.getEntity().getId())
                          .with(httpBasic(basicAuthUser, basicAuthPassword)))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(tenantFixture.getResponseModel().getTenantName())));
	}

  @Test
	public void test_create() throws Exception {
		when(tenantService.create(tenantFixture.getRequestModel())).thenReturn(tenantFixture.getResponseModel());
    this.mockMvc.perform(post("/tenant")
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(tenantFixture.getRequestModelAsJson())
                          .with(httpBasic(basicAuthUser, basicAuthPassword)))
                          .andDo(print())
                          .andExpect(status().isCreated())
				                  .andExpect(content().string(containsString(tenantFixture.getResponseModel().getTenantName())));
	}

  @Test
  public void delete_by_id() throws Exception {
    this.mockMvc.perform(delete("/tenant/{id}", tenantFixture.getEntity().getId())
        .with(httpBasic(basicAuthUser, basicAuthPassword)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
	public void test_patch() throws Exception {
		when(tenantService.patch(tenantFixture.getRequestModelAsMap(), tenantFixture.getEntity().getId())).thenReturn(tenantFixture.getResponseModel());
    this.mockMvc.perform(patch("/tenant/{id}", tenantFixture.getEntity().getId())
                          .contentType(APPLICATION_JSON_UTF8)
                          .content(tenantFixture.getRequestModelAsJson())
                          .with(httpBasic(basicAuthUser, basicAuthPassword)))
                          .andDo(print())
                          .andExpect(status().isOk())
				                  .andExpect(content().string(containsString(tenantFixture.getResponseModel().getTenantName())));
	}

}
