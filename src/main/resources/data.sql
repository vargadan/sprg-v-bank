create table USER (
    USERNAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    primary key(USERNAME)
);

create table ACCOUNT (
    ACCOUNT_ID VARCHAR(255) NOT NULL,
    USERNAME VARCHAR(255) NOT NULL,
    BALANCE DECIMAL NOT NULL,
    CURRENCY VARCHAR(3) NOT NULL,
    primary key(ACCOUNT_ID)
);

create table TRANSACTION (
    TRANSACTION_ID INT AUTO_INCREMENT,
    FROM_ACCOUNT VARCHAR(255) NOT NULL,
    TO_ACCOUNT VARCHAR(255) NOT NULL,
    AMOUNT DECIMAL NOT NULL,
    CURRENCY VARCHAR(3) NOT NULL,
    COMMENT VARCHAR(1000),
    executed BOOL NOT NULL,
    primary key(TRANSACTION_ID)
);

INSERT INTO USER(USERNAME, PASSWORD) VALUES ('Attacker','Attacker_01');
INSERT INTO USER(USERNAME, PASSWORD) VALUES ('Victim','Victim_01');
INSERT INTO USER(USERNAME, PASSWORD) VALUES ('Brother','Brother_01');

INSERT INTO ACCOUNT VALUES('1-123456-01','Victim', 100000, 'CHF');
INSERT INTO ACCOUNT VALUES('1-123456-02','Brother', 10000, 'CHF');
INSERT INTO ACCOUNT VALUES('1-123456-07','Attacker', 100, 'CHF');

COMMIT;

select count(*) from TRANSACTION;

delete from TRANSACTION;
update ACCOUNT set balance = 20000;