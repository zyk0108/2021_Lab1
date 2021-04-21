package com.example.test.Entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class AccountInfo {

    @NonNull String accountNum;
    @NonNull int loan;
    @NonNull int deposit;
    @NonNull int fine;
}
