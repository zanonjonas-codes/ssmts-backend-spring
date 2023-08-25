package com.zanonjonascodes.ssmts.tenant;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tenant")
@Data
@EqualsAndHashCode(callSuper=false)
public class TenantEntity extends BaseEntity {

  protected String companyName;

  protected String logoImgUrl;
}
