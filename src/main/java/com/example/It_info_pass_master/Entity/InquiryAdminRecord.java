package com.example.It_info_pass_master.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InquiryAdminRecord(@NotNull int id, @NotBlank String name, @NotBlank String inquiryTitle,int checkInquiry) {
}
