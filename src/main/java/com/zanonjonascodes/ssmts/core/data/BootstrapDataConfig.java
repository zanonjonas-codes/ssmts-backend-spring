package com.zanonjonascodes.ssmts.core.data;

import javax.sql.DataSource;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.zanonjonascodes.ssmts.role.RoleRepository;
import com.zanonjonascodes.ssmts.user.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BootstrapDataConfig {

  protected DataSource dataSource;

  protected UserRepository userRepository;

  protected RoleRepository roleRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void loadData() {
    if (roleRepository.count() == 0 && userRepository.count() == 0) {
      ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
          new ClassPathResource("sql/initial_data.sql"));

      resourceDatabasePopulator.execute(dataSource);
    }
  }
}