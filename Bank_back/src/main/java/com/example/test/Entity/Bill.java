package com.example.test.Entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Bill {

    @NonNull int loan_num;
    @NonNull int customerCode;
    @NonNull int cardID;
    @NonNull int periodNum;
    @NonNull double LoanAmount;
    @NonNull double remainingForPay;
    @NonNull Date dueTime;
    @NonNull String overdue;
    @NonNull double fine;
    @NonNull String alreadyFine;
    @NonNull String alreadyPay;
}
