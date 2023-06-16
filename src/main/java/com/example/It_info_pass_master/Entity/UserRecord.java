package com.example.It_info_pass_master.Entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRecord(int id, @NotBlank String loginId, @NotBlank String password, @NotBlank String name,@NotBlank String role) {
}

//todo responsibleIdをroleに変更する
