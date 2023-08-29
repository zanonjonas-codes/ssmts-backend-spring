package com.zanonjonascodes.ssmts.role;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "role")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class RoleEntity extends BaseEntity<String> implements GrantedAuthority {

  protected String role;

  @ManyToMany(mappedBy = "grantedRoles")
  List<UserEntity> grantedUsers;

  @Override
  public String getAuthority() {
    return getRole();
  }

}
