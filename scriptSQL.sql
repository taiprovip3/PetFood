
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
	last_name nvarchar(255),
	balance money,
	address VARCHAR(255)
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
create table order(
	order_id int IDENTITY(1,1) PRIMARY KEY,
	status VARCHAR(255),
	order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	shipped_date DATETIME,
	quantity int,
	total_price money,
	user_id int FOREIGN KEY REFERENCES users(user_id),
	product_id int FOREIGN KEY REFERENCES products(product_id)
);






go
insert into categories (category_name) values ('KIBBLE');
insert into categories (category_name) values ('DEHYDRATED');
insert into categories (category_name) values ('CANNED');
insert into categories (category_name) values ('SEMI-MOIST');
insert into categories (category_name) values ('HOMEMADE');
insert into categories (category_name) values ('RAW MEAT-BASED');

insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Royal Canin','DOG','The best of popular pet food bought and used for dogs and cats', 185.0, 4.5, 3600,'royal_canin.jpg',1);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Smart Heart','CAT','Strongest label in labor economic buisiness, very heath impact to cats and dogs heart', 222.0, 5.1, 2100,'smart_heart.png',2);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Classic Pets','ALL','Very light and smuff delicious food for both dogs and cats. This was produced in 1988 factory until now!', 159.0, 6.3, 1360,'classic_pets.png',3);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Pedigree','DOG','Pedigree dog food is a brand of Mars corporation (USA). Products include all types of dog food. Pedigree is a ready-to-eat wet food, so it is suitable for small pet dogs such as chihuahuas, foxes, poodles, etc.', 168.0, 5.1, 2390,'pedigree.png',4);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Winner','CAT','This is a food exclusively for large dogs, especially good for becgie, malinois, pitbull, rottweiler, doberman', 129.0, 4.6, 3600,'winner.png',5);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('ANF','ALL','ANF ​​dog food is a soft food line specializing in lamb, so it is suitable for 6-month-old puppies or nursing dogs.', 200.0, 4.8, 1560,'anf.png',6);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Fib','DOG','Fibs is a product line that is suitable for all dog breeds and for all sizes from small to large.', 176.0, 4.9, 1862,'fib.png',1);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Fitmin','CAT','This is a brand of dry food for large breeds and adult dogs like the Puppy. Food is made entirely from fresh meat and then dried, so it retains maximum nutrients.', 159.0, 6.0, 2874,'fitmin.png',2);
insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
	values ('Zenith','ALL','Providing soft grain food for puppies, Zeniths products contain many nutrients such as protein, starch, protein', 182.0, 5.5, 2235,'zenith.png',3);

insert into roles (role_name) values ('MEMBER');
insert into roles (role_name) values ('ADMIN');

insert into users (user_name,crypted_password,email,first_name,last_name,balance,address,role_id) values
(N'nano',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','nano@gmail.com',N'Client',N'A',0,'Simple roofing, 150 COLOMBIA, STREET 15, NEWYORK USA','1');
insert into users (user_name,crypted_password,email,first_name,last_name,balance,address,role_id) values
(N'susan',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','susan@gmail.com',N'Admin',N'B',0,'Red boot, 151 DOMINICA, STREET 16, LOSANGELES USA','2');
insert into users (user_name,crypted_password,email,first_name,last_name,balance,address,role_id) values
(N'papa',N'$2a$10$TCnFcIb0ulVx0wRnjuM8E.glduflWD7fmRxXR1gieW/T4xrfoAIWe','papa@gmail.com',N'Client',N'C',0,'Free style, 152 MARTINIQUE, STREET 17, CHICAGO USA','1');

insert into cart (cart_id, product_id, user_id, price, quantity) values (1,1,1,232,1);
insert into cart (cart_id, product_id, user_id, price, quantity) values (2,2,1,503,3);

insert into user_roles (user_id,role_id) values (1,1);
insert into user_roles (user_id,role_id) values (2,1);
insert into user_roles (user_id,role_id) values (2,2);
insert into user_roles (user_id,role_id) values (3,1);

insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,1,200,1,1);
insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,1,201,1,2);
insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,1,202,1,3);
insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,2,508,2,4);
insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,1,302,2,5);
insert into orders (status,shipped_date,quantity,total_price,user_id,product_id) values ('PENDING',CURRENT_TIMESTAMP,5,1025,2,6);






--select * from products where product_type = 'DOG' or product_type = 'CAT'
--select * from categories
--select * from user_roles
--select * from roles
--select * from users
--select * from cart
--select * from products p join categories c on p.category_id = c.category_id 
--where p.product_type = 'DOG' and c.category_name = 'KIBBLE'
--select * from products a1 join categories a2 on a1.category_id = a2.category_id
--select b.role_name from user_roles a join roles b on a.role_id = b.role_id where a.user_id = 2
--delete from users where user_id = 4
--delete from products where category_id = 'NULL'
--select * from cart where product_id = 1 and user_id = 1
--select * from orders