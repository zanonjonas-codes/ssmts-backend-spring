package com.zanonjonascodes.ssmts.user;

import java.util.Set;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;
import com.zanonjonascodes.ssmts.role.RoleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends BaseEntity<String> {

  @NotNull
  @Email
  @Column(unique = true)
  protected String email;

  @NotNull
  protected String password;

  @NotNull
  protected String firstName;

  @NotNull
  protected String lastName;

  @NotNull
  protected Boolean isEnabled;

  @ManyToMany(mappedBy = "grantedUsers", fetch = FetchType.EAGER)
  protected Set<RoleEntity> grantedRoles;

}
