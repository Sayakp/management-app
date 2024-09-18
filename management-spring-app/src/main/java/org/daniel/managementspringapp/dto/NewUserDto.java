package org.daniel.managementspringapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    private Long id;
    @NotBlank(message = "Username is mandatory")
    @Size(min =3, message = "Username must be at least 3 characters long")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
}
