package com.example.It_info_pass_master.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InquiryRecord(@NotNull int id, @NotBlank String inquiryTitle, @NotBlank String inquiryText,
                            @NotNull int userId, String inquiryAnswer, int checkInquiry, int readInquiry) {
}
