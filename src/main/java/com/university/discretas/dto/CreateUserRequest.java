package com.university.discretas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.university.discretas.validation.DocumentIdentityConstraint;
import com.university.discretas.validation.EmailConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest implements Serializable {

	@DocumentIdentityConstraint(message = "Documento de Identidad ya Existe")
	@NotEmpty(message = "El Documento de Identidad es Requerido")
	private String identityDocument;

	@NotEmpty(message = "El Nombre es Requerido")
	private String firstName;

	@NotEmpty(message = "El Apellido es Requerido")
	private String lastName;


	@EmailConstraint(message = "Email ya Existe")
	@NotNull(message = "Email es Requerida")
	@Email
	private String email;

	@NotEmpty(message = "Telefono es Requerido")
	private String phone;

	private String password;

}
