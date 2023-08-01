create sequence pet_images_seq start with 1 increment by 50;
create sequence pets_seq start with 1 increment by 50;

create table deleted_users
(
    date_of_creation timestamp(6),
    date_of_deletion timestamp(6),
    user_id          bigint not null,
    email            varchar(255),
    first_name       varchar(255),
    phone_number     varchar(255),
    primary key (user_id)
);
create table feature_properties
(
    property_id    bigserial not null,
    property_value varchar(255),
    primary key (property_id)
);
create table features
(
    date        date,
    feature_id  bigserial not null,
    property_id bigint,
    user_id     bigint,
    description varchar(255),
    primary key (feature_id)
);
create table pet_images
(
    image_id        bigint not null,
    image_size      bigint,
    image_type      varchar(255),
    image_uuid_name varchar(255) unique,
    image_data      oid,
    primary key (image_id)
);
create table pets
(
    sex      smallint check (sex between 0 and 1),
    image_id bigint unique,
    pet_id   bigint not null,
    breed    varchar(255),
    chip_id  varchar(255) unique,
    pet_name varchar(255),
    type     varchar(255),
    primary key (pet_id)
);
create table pets_features
(
    feature_id bigint not null,
    pet_id     bigint not null
);
create table social_networks
(
    property_id   bigserial not null,
    base_url      varchar(255),
    property_name varchar(255),
    primary key (property_id)
);
create table user_roles
(
    user_id bigint not null,
    roles   varchar(255) check (roles in ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PRIVILEGED_ACCESS'))
);
create table users
(
    active                          boolean,
    permission_to_show_email        boolean,
    permission_to_show_phone_number boolean,
    date_of_creation                timestamp(6),
    user_id                         bigserial not null,
    password                        varchar(128),
    email                           varchar(255) unique,
    first_name                      varchar(255),
    phone_number                    varchar(255),
    primary key (user_id)
);
create table users_pets
(
    pet_id  bigint not null,
    user_id bigint not null
);
create table users_social_networks
(
    property_id       bigint,
    social_network_id bigserial not null,
    user_id           bigint,
    short_name        varchar(255),
    primary key (social_network_id)
);

alter table if exists features
    add constraint FK5od0ok6w5gkel643o664oppsv foreign key (property_id) references feature_properties;

alter table if exists features
    add constraint FKsrjefbl6syjkfcp8ncvd9wlrb foreign key (user_id) references users;

alter table if exists pets
    add constraint FK1l7f202me39foefj4kxqk0fsx foreign key (image_id) references pet_images;

alter table if exists pets_features
    add constraint FKjohldp882s5eemkfum8bcw1dt foreign key (feature_id) references features;

alter table if exists pets_features
    add constraint FK1yeliobt4fphwtomrwcgk028n foreign key (pet_id) references pets;

alter table if exists user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;

alter table if exists users_pets
    add constraint FKh0oi8jiycvm4906ygq1igd2h6 foreign key (pet_id) references pets;

alter table if exists users_pets
    add constraint FK7ud46jdyaqmpgffridbwvyeup foreign key (user_id) references users;

alter table if exists users_social_networks
    add constraint FKmcusky88wtg65015oc4cb50ug foreign key (property_id) references social_networks;

alter table if exists users_social_networks
    add constraint FKqn120qemthyvrapl9kymsda1w foreign key (user_id) references users;