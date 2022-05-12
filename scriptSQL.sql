﻿

use master;
go
create database petfood;
go
use petfood;
go









create table categories(
	category_id int IDENTITY(1,1) PRIMARY KEY,
	category_name varchar(255)
);
create table products(
	product_id int IDENTITY(1,1) PRIMARY KEY,
	product_name varchar(255),
	product_type varchar(255),
	product_description varchar(255),
	product_price money,
	product_weight money,
	product_quantity int,
	product_image varchar(255),
	category_id int FOREIGN KEY REFERENCES categories(category_id)
);
create table users(
	user_id int IDENTITY(1,1) PRIMARY KEY,
	user_name varchar(255),
	crypted_password varchar(255),
	email varchar(255),
	first_name nvarchar(255),
	last_name nvarchar(255)
);
create table roles(
	role_id int IDENTITY(1,1) PRIMARY KEY,
	role_name varchar(255)
);
create table user_roles(
	id int IDENTITY(1,1) PRIMARY KEY,
	user_id int FOREIGN KEY REFERENCES users(user_id),
	role_id int FOREIGN KEY REFERENCES roles(role_id)
);
create table carts(
	cart_id int IDENTITY(1,1) PRIMARY KEY,
	product_id int FOREIGN KEY REFERENCES products(product_id),
	user_id int FOREIGN KEY REFERENCES users(user_id)
);






go
insert into categories (category_name) values ('KIBBLE');
insert into categories (category_name) values ('DEHYDRATED');
insert into categories (category_name) values ('CANNED');
insert into categories (category_name) values ('SEMI-MOIST');
insert into categories (category_name) values ('HOMEMADE');
insert into categories (category_name) values ('RAW MEAT-BASED');

insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Royal Canin','DOG','The best of popular pet food bought and used for dogs and cats', 185.0, 4.5, 3600,'C://images/royal_canin.png',1);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Smart Heart','CAT','Strongest label in labor economic buisiness, very heath impact to cats and dogs heart', 222.0, 5.1, 2100,'C://images/smart_heart.png',2);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Classic Pets','ALL','Very light and smuff delicious food for both dogs and cats. This was produced in 1988 factory until now!', 159.0, 6.3, 1360,'C://images/classis_pets.png',3);

insert into users (user_name,crypted_password,email,first_name,last_name) values
(N'nano',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','nano@gmail.com',N'Client',N'A');
insert into users (user_name,crypted_password,email,first_name,last_name) values
(N'susan',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','susan@gmail.com',N'Admin',N'B');
insert into users (user_name,crypted_password,email,first_name,last_name) values
(N'papa',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','papa@gmail.com',N'Client',N'C');

insert into roles (role_name) values ('MEMBER');
insert into roles (role_name) values ('ADMIN');

insert into cart (product_id, user_id) values (1,1);
insert into cart (product_id, user_id) values (2,1);

insert into user_roles (user_id,role_id) values (1,1);
insert into user_roles (user_id,role_id) values (2,1);
insert into user_roles (user_id,role_id) values (2,2);







select * from products where product_type = 'DOG' or product_type = 'CAT'
select * from categories
select * from user_roles
select * from roles
select * from users
select * from cart
select * from products p join categories c on p.category_id = c.category_id 
where p.product_type = 'DOG' and c.category_name = 'KIBBLE'
select * from products a1 join categories a2 on a1.category_id = a2.category_id
select b.role_name from user_roles a join roles b on a.role_id = b.role_id where a.user_id = 2
delete from users where user_id = 4