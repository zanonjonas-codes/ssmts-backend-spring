package com.zanonjonascodes.ssmts;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.zanonjonascodes.ssmts")
@EntityScan(basePackages = "com.zanonjonascodes.ssmts")
public class SsmtsTestConfig {
  
}
