package com.trendyol.basketcore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    private String id;

    @Column(name = "user_id")
    @NotNull
    private String userId;
}
