-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into person_data (id, firstname, lastName, ssn, dob) values (101, 'John', 'Doe', 1234, '2020-02-02');
insert into person_data (id, firstname, lastName, ssn, dob) values (102, 'Jane', 'Doe', 4321, '2020-02-02');
insert into person_data (id, firstname, lastName, ssn, dob) values (103, 'Yaha', 'Doe', 5678, '1990-01-01');
insert into person_data (id, firstname, lastName, ssn, dob) values (104, 'Mary', 'Smith', 1357, '2003-01-01');
insert into person_data (id, firstname, lastName, ssn, dob) values (105, 'Maryam', 'Smith', 7531, '2007-01-01');

insert into customer (id, name, age) values (101, 'John Doe', 21);
insert into customer (id, name, age) values (102, 'XYZ', 22);
