drop table if exists lab1.accountinfo;
drop table if exists lab1.products;
drop table if exists lab1.purchase_info;
drop table if exists lab1.transaction;

create table if not exists lab1.accountinfo
(
    accountNum varchar(50)   not null,
    loan       int default 0 not null,
    deposit    int default 0 not null,
    fine       int default 0 not null
);

create table if not exists lab1.products
(
    type  int   not null,
    rate  float not null,
    term  int   null,
    price int   not null
);

create table if not exists lab1.purchase_info
(
    account     varchar(50) not null,
    productType int         not null,
    time        date        not null,
    amount      int         not null,
    duration    int         not null
);

create table if not exists lab1.transaction
(
    account   varchar(50)  not null,
    time      date         not null,
    operation varchar(255) not null,
    amount    int          not null
);


# 初始化数据表
# insert
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('0', 10000, 20000, 250);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('1', 10000, 566000, 0);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('2', 10000, 20000, 0);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('3', 50000, 30000, 0);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('4', 10000, 10000, 0);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('5', 10000, 20000, 9999);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('6', 10000, 10000, 250);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('7', 10000, 1000, 250);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('8', 10000, 100, 250);
INSERT INTO lab1.accountinfo (accountNum, loan, deposit, fine) VALUES ('9', 20000, 30000, 0);


#table
INSERT INTO lab1.products (type, rate, term, price) VALUES (1, 0, 1, 1000);
INSERT INTO lab1.products (type, rate, term, price) VALUES (2, 0, 3, 2000);
INSERT INTO lab1.products (type, rate, term, price) VALUES (3, 0, 3, 5000);

#table
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('1', 2, '2021-04-11', 4000, 3);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('1', 1, '2021-04-11', 4000, 3);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('1', 3, '2021-04-11', 4000, 3);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('1', 2, '2021-04-11', 6000, 4);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('4', 2, '2021-04-11', 2000, 1);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('4', 2, '2021-04-11', 20000, 6);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('4', 2, '2021-04-11', 20000, 6);
INSERT INTO lab1.purchase_info (account, productType, time, amount, duration) VALUES ('1', 2, '2021-04-13', 22000, 10);

#insert
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '存入', 2000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('2', '2021-04-12', '存入', 10000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('2', '2021-04-12', '贷款', 30000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 4000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 4000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 4000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 4000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 4000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-11', '购买产品2', 6000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '交罚金', 20000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '购买产品2', 2000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '交罚金', 20000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '购买产品2', 20000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '交罚金', 20000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('4', '2021-04-11', '购买产品2', 20000);
INSERT INTO lab1.transaction (account, time, operation, amount) VALUES ('1', '2021-04-13', '购买产品2', 22000);
