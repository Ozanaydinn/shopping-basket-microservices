package com.trendyol.notificationcore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLookupDTO implements Serializable {
    private List<UserDTO> users;
    private String message;
}
