package com.example.BackEndSem4.response.user;

import com.example.BackEndSem4.models.Role;
import com.example.BackEndSem4.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("is_active")
    private boolean active;

    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthday;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("role")
    private Role role;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
