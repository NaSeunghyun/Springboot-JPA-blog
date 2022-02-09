package com.nas.blog.controller.form;

import com.nas.blog.model.RoleType;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class JoinForm {

    @NotNull
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    private String password;

    @NotBlank
    private String userName;

    private RoleType role = RoleType.USER;
}
