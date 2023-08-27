package com.zanonjonascodes.ssmts.user;

import com.zanonjonascodes.ssmts.core.data.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends BaseEntity<String> {

}
