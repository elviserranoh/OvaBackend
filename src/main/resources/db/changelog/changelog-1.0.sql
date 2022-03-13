--liquibase formatted sql

--changeset elvis.serrano:tb_users_changelog.0.1 context:dev,prod
--comment tb_users creation tag: tb_users_changelog.0.1

--
-- TB_USERS
--

create table TB_USERS(
    id bigint primary key auto_increment,
    identity_document varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    phone varchar(255) not null,
    rol varchar(20) not null,
    image text
);

INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('23805156', 'Elvis', 'Serrano', 'elviserranoh@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_ADMIN');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('20346913', 'Eduardo', 'Sanchez', 'ed009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('24129908', 'Luisana', 'Maza', 'luisana009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('20575289', 'Carlos', 'Serrano', 'krlos009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('23805158', 'Ysabel', 'Serrano', 'ysaserrano@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('9980272', 'Linda', 'Henriquez', 'lindahenriquez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('5084188', 'Simon', 'Velazquez', 'velazquez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('7773838', 'Alguno', 'Mas', 'algunomas@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('1111223', 'Sergio', 'Jimenez', 'jimenez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('3333221', 'Irene', 'Serrano', 'Irene@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('5554422', 'Luiguie', 'Buffon', 'Luiguie@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO TB_USERS(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('7788283', 'Ricardo', 'Argona', 'Ricardo@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');

--rollback DROP TABLE TB_USERS

--changeset elvis.serrano:tb_ovas_changelog.0.1 context:dev,prod
--comment tb_ovas creation tag: tb_ovas_changelog.0.1

--
-- TB_OVAS
--

create table TB_OVAS(
  id bigint primary key auto_increment,
  name varchar(255) not null,
  description varchar(255) not null,
  image text
);

--rollback DROP TABLE TB_TOPICS

--changeset elvis.serrano:tb_topics_changelog.0.1 context:dev,prod
--comment tb_topics creation tag: tb_topics_changelog.0.1

--
-- TB_TOPICS
--

create table TB_TOPICS(
                        id bigint primary key auto_increment,
                        index int not null,
                        title varchar(255) not null,
                        objetive varchar(255) not null,
                        ova_id bigint references TB_OVAS(id),
                        image text
);

--rollback DROP TABLE tb_topics

--changeset elvis.serrano:tb_topic_contents_changelog.0.1 context:dev,prod
--comment tb_topic_contents creation tag: tb_topic_contents_changelog.0.1

--
-- TB_TOPIC_CONTENTS
--

create table TB_TOPIC_CONTENTS(
                          id bigint primary key auto_increment,
                          content varchar(255) not null,
                          topic_id bigint references TB_TOPICS(id)
);

--rollback DROP TABLE tb_topic_contents

--changeset elvis.serrano:TB_SUBJECT_MATTER_changelog.0.1 context:dev,prod
--comment TB_SUBJECT_MATTER creation tag: TB_SUBJECT_MATTER_changelog.0.1

--
-- TB_SUBJECT_MATTER
--

create table TB_SUBJECT_MATTER(
                                  id bigint primary key auto_increment,
                                  index integer not null,
                                  type varchar(255) not null,
                                  question text not null,
                                  topic_id bigint references TB_TOPICS(id),
                                  image text
);

--rollback DROP TABLE TB_SUBJECT_MATTER

--changeset elvis.serrano:TB_SUBJECT_MATTER_COLUMN_changelog.0.1 context:dev,prod
--comment TB_SUBJECT_MATTER_COLUMN creation tag: TB_SUBJECT_MATTER_COLUMN_changelog.0.1

--
-- TB_SUBJECT_MATTER_COLUMN
--

create table TB_SUBJECT_MATTER_COLUMN(
                                         id bigint primary key auto_increment,
                                         content varchar(255) not null,
                                         subject_matter_id bigint references TB_SUBJECT_MATTER(id)
);

--rollback DROP TABLE TB_SUBJECT_MATTER_COLUMN

--changeset elvis.serrano:TB_SUBJECT_MATTER_ROW_changelog.0.1 context:dev,prod
--comment TB_SUBJECT_MATTER_ROW creation tag: TB_SUBJECT_MATTER_ROW_changelog.0.1

--
-- TB_SUBJECT_MATTER_ROW
--

create table TB_SUBJECT_MATTER_ROW(
                                         id bigint primary key auto_increment,
                                         content varchar(255) not null,
                                         subject_matter_id bigint references TB_SUBJECT_MATTER(id)
);

--rollback DROP TABLE TB_SUBJECT_MATTER_ROW

--changeset elvis.serrano:TB_SUBJECT_MATTER_ANSWER_CORRECT_changelog.0.1 context:dev,prod
--comment TB_SUBJECT_MATTER_ANSWER_CORRECT creation tag: TB_SUBJECT_MATTER_ANSWER_CORRECT_changelog.0.1

--
-- TB_SUBJECT_MATTER_ANSWER_CORRECT
--

create table TB_SUBJECT_MATTER_ANSWER_CORRECT(
                                                 id bigint primary key auto_increment,
                                                 subject_matter_id bigint references TB_SUBJECT_MATTER(id),
                                                 subject_matter_row_id bigint references TB_SUBJECT_MATTER_ROW(id),
                                                 subject_matter_column_id bigint references TB_SUBJECT_MATTER_COLUMN(id)
);

--rollback DROP TABLE TB_SUBJECT_MATTER_ANSWER_CORRECT

--changeset elvis.serrano:TB_DOCUMENTS_changelog.0.1 context:dev,prod
--comment TB_DOCUMENTS creation tag: TB_DOCUMENTS_changelog.0.1

--
-- TB_DOCUMENTS
--

CREATE TABLE TB_DOCUMENTS(
                             id bigint primary key auto_increment,
                             title varchar(255) not null,
                             autor varchar(255) not null,
                             year integer not null,
                             presentation text not null,
                             url text,
                             image text,
                             document text,
                             ova_id bigint references TB_OVAS(id)
);

--rollback DROP TABLE TB_DOCUMENTS

--changeset elvis.serrano:TB_FEED_changelog.0.1 context:dev,prod
--comment TB_DOCUMENTS creation tag: TB_FEED_changelog.0.1

--
-- TB_FEED
--

CREATE TABLE TB_FEED(
                             id bigint primary key auto_increment,
                             description text not null
);

--rollback DROP TABLE TB_EVENTS

--changeset elvis.serrano:TB_EVENTS_changelog.0.1 context:dev,prod
--comment TB_EVENTS creation tag: TB_EVENTS_changelog.0.1

--
-- TB_EVENTS
--

CREATE TABLE TB_EVENTS(
                          id bigint primary key auto_increment,
                          description text,
                          location text not null,
                          date date not null,
                          time time not null
);

--rollback DROP TABLE TB_EVENTS