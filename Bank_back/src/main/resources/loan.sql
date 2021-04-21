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

create table accountinfo
(
    accountNum varchar(50)   not null,
    loan       int default 0 not null,
    deposit    int default 0 not null,
    fine       int default 0 not null
);

create table products
(
    type  int   not null,
    rate  float not null,
    term  int   null,
    price int   not null
);

create table purchase_info
(
    account     varchar(50) not null,
    productType int         not null,
    time        date        not null,
    amount      int         not null,
    duration    int         not null
);

create table transaction
(
    account   varchar(50)  not null,
    time      date         not null,
    operation varchar(255) not null,
    amount    int          not null
);



