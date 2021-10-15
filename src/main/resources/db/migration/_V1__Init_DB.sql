create table equip (
  id bigint not null auto_increment,
  cost double precision,
  grade integer,
  equip_type_id bigint,
  primary key (id)
) ;


create table equip_type (
  id bigint not null auto_increment,
  name varchar(255),
primary key (id)
) ;

create table event (
  id bigint not null auto_increment,
  uuid varchar(255),
  active bit,
  cost double precision,
  date_closed varchar(255),
  date_created varchar(255),
  event_type_id bigint,
primary key (id)
);


create table event_screenshots (
  events_id bigint not null,
  screenshots_id bigint not null
);


create table event_type (
  id bigint not null auto_increment,
  name varchar(255),
  score integer,
primary key (id)
) ;

create table level (
  id bigint not null auto_increment,
  cost double precision,
  value integer,
primary key (id)
);

create table prof (
  id bigint not null auto_increment,
  cost double precision,
  value varchar(255),
primary key (id)
);

create table screenshot (
  id bigint not null auto_increment,
  filename varchar(255),
primary key (id)
);

create table user (
  id bigint not null auto_increment,
  active bit,
  coefficient double precision,
  confirm bit,
  email varchar(255),
  password varchar(255) not null,
  points double precision,
  reg_screenshot varchar(255),
  requested bit,
  token varchar(255),
  username varchar(255) not null,
  validate bit,
  level_id bigint,
  prof_id bigint,
primary key (id)
) ;

create table user_approved_events (
  approved_users_id bigint not null,
  approved_events_id bigint not null,
primary key (approved_users_id, approved_events_id)
) ;

create table user_equip (
  user_id bigint not null,
  equip_id bigint not null
);

create table user_events (
  users_id bigint not null,
  events_id bigint not null,
primary key (users_id, events_id)
);

create table user_role (
  user_id bigint not null,
  roles varchar(255)
);
create table user_screenshots (
  users_id bigint not null,
  screenshots_id bigint not null
);

alter table equip add constraint FK48sykcrajmcj80hju9kjudemb
       foreign key (equip_type_id)
references equip_type (id);

alter table event
add constraint FKgxoo7ftgbsrwr4i27wb9ylu1
       foreign key (event_type_id)
references event_type (id);

alter table event_screenshots
add constraint FK7v3gk8u8c5yei99k0fck92emn
       foreign key (screenshots_id)
references screenshot (id);

alter table event_screenshots
add constraint FKo1hv3w3u8q9uwh0fnvyxksng7
       foreign key (events_id)
references event (id);

alter table user
add constraint FK4do7xomli51oxpc6b5j97jap0
       foreign key (level_id)
references level (id);

alter table user
add constraint FKrm8ec5mbyv60bdxguqrnexymp
       foreign key (prof_id)
references prof (id);

alter table user_approved_events
add constraint FK5jymywvnmltk12rktwb0er5qs
       foreign key (approved_events_id)
references event (id);

alter table user_approved_events
add constraint FK9s6wwj40496urdfe7m2djsepc
       foreign key (approved_users_id)
references user (id);

alter table user_equip
add constraint FKn20mbjapdvja8s23i5hb2fx4d
       foreign key (equip_id)
references equip (id);

alter table user_equip
add constraint FK2wp0qiw56p0j0i41epe2o1obs
       foreign key (user_id)
references user (id);

alter table user_events
add constraint FKaoq9ta3w34ph5j77xx2dgfao6
       foreign key (events_id)
references event (id);

alter table user_events
add constraint FK72unecwaa590jdpxhp7jto3r4
       foreign key (users_id)
references user (id);

alter table user_role
add constraint FK859n2jvi8ivhui0rl0esws6o
       foreign key (user_id)
references user (id);

alter table user_screenshots
add constraint FK22bm073s6b9unngpqx53y7510
       foreign key (screenshots_id)
references screenshot (id);

alter table user_screenshots
add constraint FKhyltr6uqlorsrpl6usl9hs5l4
       foreign key (users_id)
references user (id);
