package com.springbank.user.query.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
public class UserDTO implements Serializable {
    private String id;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String basketId;
}
