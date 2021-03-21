create table users(
id integer identity primary key,
full_name varchar(50),
date_of_birth date,

);

create table address(
id integer identity primary key ,
users integer references users(id),
address_line varchar(250)
);

create table books(
id integer identity primary key,
title varchar(250),
isbn varchar(250)
);

create table authors(
id integer identity primary key,
name varchar(50),
dob date
);

create table book_author(
author integer,
books integer,
primary key(books,author)
);

create table cineplex(
id integer identity primary key,
movie_title varchar(50),
lead_actor varchar(50),
movie_duration integer,
ticket_price decimal
);

create table purchase(
id integer identity primary key,
quantity integer,
show_time varchar(50),
price decimal
);
