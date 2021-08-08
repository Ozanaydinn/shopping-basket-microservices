package com.springbank.user.query.api.amqp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbank.user.core.models.User;
import com.springbank.user.query.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedMessage implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user")
    private UserDTO user;
}
