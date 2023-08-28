package com.zanonjonascodes.ssmts.role;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

  @ManyToMany
  @JoinTable(name = "role_granted_users", joinColumns = @JoinColumn(name = "granted_roles_id"), inverseJoinColumns = @JoinColumn(name = "granted_users_id"))
  Set<UserEntity> grantedUsers;

  @Override
  public String getAuthority() {
    return getRole();
  }

}
