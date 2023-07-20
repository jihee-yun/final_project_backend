package com.kh.finalProject.dto;

import com.kh.finalProject.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponseDto {
    private String adminId;
    private String password;
    public static AdminResponseDto of(Admin admin) {
        return AdminResponseDto.builder()
                .adminId(admin.getAdminId())
                .password(admin.getPassword())
                .build();
    }
}
