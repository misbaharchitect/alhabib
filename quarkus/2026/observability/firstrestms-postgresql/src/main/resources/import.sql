-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;


insert into student (id, name, age, grade, dob) values(101, 'Alice', 20, 5, '2003-05-15');
insert into student (id, name, age, grade, dob) values(102, 'Bob', 22, 5, '2003-05-15');
insert into student (id, name, age, grade, dob) values(103, 'Charlie', 21, 5, '2003-05-15');

insert into employees_data (id, name, age, doj, type) values(201, 'Andre', 20, '2003-05-15', 'full-time');
insert into employees_data (id, name, age, doj, type) values(202, 'Brian', 22, '2003-05-15', 'part-time');
insert into employees_data (id, name, age, doj, type) values(203, 'Caroline', 21, '2003-05-15', 'full-time');