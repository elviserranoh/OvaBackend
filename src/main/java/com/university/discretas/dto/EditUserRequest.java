package com.university.discretas.dto;

import com.university.discretas.validation.DocumentIdentityConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class EditUserRequest implements Serializable {

	private Long id;

	private String identityDocument;

	@NotEmpty(message = "El Nombre es Requerido")
	private String firstName;

	@NotEmpty(message = "El Apellido es Requerido")
	private String lastName;

	@NotNull(message = "Email es Requerida")
	@Email
	private String email;

	@NotEmpty(message = "Telefono es Requerido")
	private String phone;

	private String password;

}
