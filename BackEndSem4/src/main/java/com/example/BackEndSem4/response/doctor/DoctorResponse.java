package com.example.BackEndSem4.response.doctor;


import com.example.BackEndSem4.models.Doctor;
import com.example.BackEndSem4.response.BaseResponse;
import com.example.BackEndSem4.response.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse extends BaseResponse {
    private  Long id;

    @JsonProperty("user")
    private UserResponse userResponse;

    @JsonProperty("specialty_id")
    private Long specialtyId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("experience")
    private Float experience;

    @JsonProperty("qualification")
    private String qualification;

    @JsonProperty("active")
    private boolean active;


    public static DoctorResponse fromDoctorResponse(Doctor doctor) {
        doctor.getUser().setRole(null);
        DoctorResponse doctorResponse = DoctorResponse.builder()
                .id(doctor.getId())
                .userResponse(UserResponse.fromUser(doctor.getUser()))
                .specialtyId(doctor.getSpecialty().getId())
                .avatar(doctor.getAvatar())
                .bio(doctor.getBio())
                .experience(doctor.getExperience())
                .qualification(doctor.getQualification())
                .active(doctor.isActive())
                .build();
        doctorResponse.setCreatedAt(doctor.getCreatedAt());
        doctorResponse.setUpdatedAt(doctor.getUpdatedAt());

        return doctorResponse;
    }
}
