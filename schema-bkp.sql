alter table if exists image_file drop index if exists fk_image_domain_file;
alter table if exists image_file drop foreign key if exists fk_image_domain_file;
drop constraint if exists uk_image_domain;

drop table if exists external_data_source;
drop table if exists image_domain;
drop table if exists image_file;

-- table ddl

create table external_data_source (
    id bigint not null auto_increment,
    host varchar(255),
    is_live bit not null,
    db_schema varchar(255),
    user_name varchar(255),
    password varchar(255),
    created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
) engine=InnoDB;

create table image_domain (
    id bigint not null auto_increment,
    domain varchar(255) not null,
    created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
) engine=InnoDB;

create table image_file (
    id bigint not null auto_increment,
    image_domain_id bigint,
    image_path varchar(255),
    created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
) engine=InnoDB;

-- constraints ddl

alter table image_domain
    add constraint uk_image_domain unique (domain);

alter table image_file
    add constraint fk_image_domain_file
    foreign key (image_domain_id)
    references image_domain (id);