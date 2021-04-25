package com.example.test.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Card {

    @NonNull int cardID;
    @NonNull int customerID;
    @NonNull double remainAmount;
}
