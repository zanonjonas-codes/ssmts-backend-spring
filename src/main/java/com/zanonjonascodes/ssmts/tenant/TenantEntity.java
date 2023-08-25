package com.zanonjonascodes.ssmts.tenant;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tenant")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
public class TenantEntity extends BaseEntity<String> {

  protected String companyName;

  protected String logoImgUrl;
}
