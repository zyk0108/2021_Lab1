drop table if exists loan_account;
drop table if exists bill;
drop table if exists card;

create table loan_account(
loan_num INT ,
customerCode INT ,
customerName varchar(255) ,
customerId INT ,
totalLoanAmount DOUBLE ,
createTime DATE,
dueTime DATE ,
type varchar(255) ,
fine DOUBLE default 0,
totalPeriodNum INT ,
thisPeriodNum INT ,
result varchar(255)
);

create table bill(
loan_num INT,
customerCode INT ,
cardID INT ,
periodNum INT ,
LoanAmount DOUBLE ,
remainingForPay DOUBLE ,
dueTime DATE ,
overdue VARCHAR(255) ,
fine DOUBLE default 0 ,
alreadyFine varchar(255),
alreadyPay varchar(255)
);

create table card(
cardID INT ,
customerID INT ,
remainAmount DOUBLE
);

insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(0,0,'test0',2020000,10000,'2021-04-10','2020-04-20','month',250,2,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(1,1,'test1',2020001,10000,'2021-04-10','2021-04-30','month',0,1,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(2,2,'test2',2020002,10000,'2021-04-10','2021-04-30','month',0,1,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(3,3,'test3',2020003,50000,'2021-04-10','2021-04-30','month',0,1,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(4,4,'test4',2020004,10000,'2021-04-10','2021-04-30','month',0,1,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(5,5,'test5',2020005,10000,'2021-04-10','2021-04-20','month',9999,1,1,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(6,6,'test6',2020006,10000,'2021-04-10','2021-04-30','month',250,2,2,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(7,7,'test7',2020007,10000,'2021-04-10','2021-04-30','month',250,2,2,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(8,8,'test8',2020008,10000,'2021-04-10','2021-04-30','month',250,2,2,'loan');
insert into loan_account (loan_num, customerCode, customerName, customerId, totalLoanAmount, createTime, dueTime, type, fine, totalPeriodNum, thisPeriodNum, result) VALUES
(9,9,'test9',2020009,20000,'2021-04-20','2021-05-10','month',0,2,1,'loan');
insert into card (cardID, customerID, remainAmount) VALUES
(0,2020000,20000);
insert into card (cardID, customerID, remainAmount) VALUES
(1,2020001,566000);
insert into card (cardID, customerID, remainAmount) VALUES
(2,2020002,20000);
insert into card (cardID, customerID, remainAmount) VALUES
(3,2020003,30000);
insert into card (cardID, customerID, remainAmount) VALUES
(4,2020004,10000);
insert into card (cardID, customerID, remainAmount) VALUES
(5,2020005,20000);
insert into card (cardID, customerID, remainAmount) VALUES
(6,2020006,10000);
insert into card (cardID, customerID, remainAmount) VALUES
(7,2020007,1000);
insert into card (cardID, customerID, remainAmount) VALUES
(8,2020008,100);
insert into card (cardID, customerID, remainAmount) VALUES
(9,2020009,30000);

insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(0,0,0,1,5000,5000,'2020-04-30','yes',0,'yes','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(0,0,0,2,5000,5000,'2020-04-20','no',250,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(1,1,1,1,10000,10000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(2,2,2,1,10000,10000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(3,3,3,1,50000,50000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(4,4,4,1,10000,10000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(5,5,5,1,10000,10000,'2021-04-30','no',9999,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(6,6,6,1,5000,5000,'2021-04-20','yes',250,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(6,6,6,2,5000,5000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(7,7,7,1,5000,5000,'2021-04-20','yes',250,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(7,7,7,2,5000,5000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(8,8,8,1,5000,5000,'2021-04-20','yes',250,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(8,8,8,2,5000,5000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(9,9,9,1,10000,10000,'2021-04-30','no',0,'no','no');
insert into bill (loan_num, customerCode, cardID, periodNum, LoanAmount, remainingForPay, dueTime, overdue, fine, alreadyFine, alreadyPay) VALUES
(9,9,9,2,10000,10000,'2021-05-10','no',0,'no','no');
