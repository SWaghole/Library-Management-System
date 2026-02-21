package com.example.LibraryProject.models.request;

import com.example.LibraryProject.models.Student;
import com.example.LibraryProject.models.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private String address;

    public Student toStudent() {
        return Student.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .address(address)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
    }
}
