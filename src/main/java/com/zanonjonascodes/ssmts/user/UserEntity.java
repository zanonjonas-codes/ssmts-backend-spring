package com.zanonjonascodes.ssmts.user;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

import jakarta.persistence.Entity;
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
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends BaseEntity<String> {

  @NotNull
  @Email
  protected String email;

  @NotNull
  protected String password;

  @NotNull
  protected String firstName;

  @NotNull
  protected String lastName;

  @NotNull
  protected Boolean isEnabled;

}
