show databases;

use sys;

show tables;

create database 4U;
use 4U;

desc (select * from tables);

create table user
(
full_name varchar(100), 
user_id int auto_increment primary key, 
email_id varchar(40) unique, 
dob datetime,
profile_pic varchar(100),
user_status varchar(100),
location varchar(50),
phone_number int unique,
gender enum('M','F')
);

create table role
(
user_id int references user(user_id),
password varchar(16),
role enum('U','A')
);

create table post
(
user_id int references user(user_id),
post_id int auto_increment primary key,
image_url varchar(200),
image_description varchar(200),
post_date datetime,
share_id int references post(post_id),
like_count int
);

create table comments
(
post_id int references post(post_id),
user_id int references user(user_id),
comment_text varchar(100),
date_time datetime
);

create table friends
(
user_id int references user(user_id),
user1_id int references user(user_id),
start_date datetime
);

alter table friends add constraint pk primary key(user_id,user1_id);

create table activity
(
activity_id int auto_increment primary key,
activity_name varchar(50)
);

create table activity_user
(
user_id int references user(user_id),
activity_id int references activity(activity_id)
);
