package com.example.test.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class LoanAccount {

    @NonNull int loan_num;
    @NonNull int customerCode;
    @NonNull String customerName;
    @NonNull int customerId;
    @NonNull double totalLoanAmount;
    @NonNull Date createTime;
    @NonNull Date dueTime;
    @NonNull String type;
    @NonNull double fine;
    @NonNull int totalPeriodNum;
    @NonNull int thisPeriodNum;
    @NonNull String result;
}
