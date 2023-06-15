package com.example.It_info_pass_master.Entity;

import jakarta.validation.constraints.NotBlank;

public record IdPassRecord(@NotBlank String loginId, @NotBlank String password) {
}
