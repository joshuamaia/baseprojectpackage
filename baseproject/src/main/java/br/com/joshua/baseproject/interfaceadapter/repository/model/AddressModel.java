package br.com.joshua.baseproject.interfaceadapter.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
@Table(name = "address")
public class AddressModel extends EntityBase<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(nullable = false, length = 100)
	String street;

	@NotEmpty
	@Column(nullable = false, length = 100)
	String district;

	Integer number;

}
