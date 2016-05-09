DROP TABLE RABBITMQ_MUTEX;
DROP SEQUENCE RABBITMQ_MUTEX_SEQ;
CREATE TABLE RABBITMQ_MUTEX ( MUTEX NUMBER(18) ,CREATED_AT TIMESTAMP );
CREATE SEQUENCE RABBITMQ_MUTEX_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 99999 CYCLE;

DROP TABLE RABBITMQ_RESERVATION;
CREATE TABLE RABBITMQ_RESERVATION(NAME VARCHAR2(32), MUTEX NUMBER(18), RESERVED_AT TIMESTAMP);