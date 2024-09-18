package com.example.BackEndSem4.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    private String address;

    @JsonProperty("birthday")
    private Date birthday;

    @JsonProperty("gender")
    private String gender;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @Min(value = 1, message = "You must enter role's Id")
    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty
    private boolean active;

}
