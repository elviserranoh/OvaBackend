--liquibase formatted sql

--changeset elvis.serrano:tb_users_changelog.0.1 context:dev,prod
--comment tb_users creation tag: tb_users_changelog.0.1

--
-- tb_users
--

create table tb_users(
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

INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('23805156', 'Elvis', 'Serrano', 'elviserranoh@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_ADMIN');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('20346913', 'Eduardo', 'Sanchez', 'ed009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('24129908', 'Luisana', 'Maza', 'luisana009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('20575289', 'Carlos', 'Serrano', 'krlos009@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('23805158', 'Ysabel', 'Serrano', 'ysaserrano@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('9980272', 'Linda', 'Henriquez', 'lindahenriquez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('5084188', 'Simon', 'Velazquez', 'velazquez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('7773838', 'Alguno', 'Mas', 'algunomas@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('1111223', 'Sergio', 'Jimenez', 'jimenez@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('3333221', 'Irene', 'Serrano', 'Irene@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('5554422', 'Luiguie', 'Buffon', 'Luiguie@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');
INSERT INTO tb_users(identity_document, first_name, last_name, email, password, phone, rol) VALUES ('7788283', 'Ricardo', 'Argona', 'Ricardo@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm', '04148090961', 'ROLE_USER');

--rollback DROP TABLE tb_users

--changeset elvis.serrano:tb_ovas_changelog.0.1 context:dev,prod
--comment tb_ovas creation tag: tb_ovas_changelog.0.1

--
-- tb_ovas
--

create table tb_ovas(
  id bigint primary key auto_increment,
  name varchar(255) not null,
  description varchar(255) not null,
  image text
);

--rollback DROP TABLE tb_ovas

--changeset elvis.serrano:tb_topics_changelog.0.1 context:dev,prod
--comment tb_topics creation tag: tb_topics_changelog.0.1

--
-- tb_topics
--

create table tb_topics(
                        id bigint primary key auto_increment,
                        indice int not null,
                        title varchar(255) not null,
                        objetive varchar(255) not null,
                        ova_id bigint references tb_ovas(id),
                        image text
);

--rollback DROP TABLE tb_topics

--changeset elvis.serrano:tb_topic_contents_changelog.0.1 context:dev,prod
--comment tb_topic_contents creation tag: tb_topic_contents_changelog.0.1

--
-- tb_topic_contents
--

create table tb_topic_contents(
                          id bigint primary key auto_increment,
                          content varchar(255),
                          topic_id bigint references tb_topics(id)
);

--rollback DROP TABLE tb_topic_contents

--changeset elvis.serrano:tb_subject_matter_changelog.0.1 context:dev,prod
--comment tb_subject_matter creation tag: tb_subject_matter_changelog.0.1

--
-- tb_subject_matter
--

create table tb_subject_matter(
                                  id bigint primary key auto_increment,
                                  indice int not null,
                                  type varchar(255) not null,
                                  question text not null,
                                  topic_id bigint references tb_topics(id),
                                  image text
);

--rollback DROP TABLE tb_subject_matter

--changeset elvis.serrano:tb_subject_matter_column_changelog.0.1 context:dev,prod
--comment tb_subject_matter_column creation tag: tb_subject_matter_column_changelog.0.1

--
-- tb_subject_matter_column
--

create table tb_subject_matter_column(
                                         id bigint primary key auto_increment,
                                         content varchar(255) not null,
                                         subject_matter_id bigint references tb_subject_matter(id)
);

--rollback DROP TABLE tb_subject_matter_column

--changeset elvis.serrano:tb_subject_matter_row_changelog.0.1 context:dev,prod
--comment tb_subject_matter_row creation tag: tb_subject_matter_row_changelog.0.1

--
-- tb_subject_matter_row
--

create table tb_subject_matter_row(
                                         id bigint primary key auto_increment,
                                         content varchar(255) not null,
                                         subject_matter_id bigint references tb_subject_matter(id)
);

--rollback DROP TABLE tb_subject_matter_row

--changeset elvis.serrano:tb_subject_matter_answer_correct_changelog.0.1 context:dev,prod
--comment tb_subject_matter_answer_correct creation tag: tb_subject_matter_answer_correct_changelog.0.1

--
-- tb_subject_matter_answer_correct
--

create table tb_subject_matter_answer_correct(
                                                 id bigint primary key auto_increment,
                                                 subject_matter_id bigint references tb_subject_matter(id),
                                                 subject_matter_row_id bigint references tb_subject_matter_row(id),
                                                 subject_matter_column_id bigint references tb_subject_matter_column(id)
);

--rollback DROP TABLE tb_subject_matter_answer_correct

--changeset elvis.serrano:tb_documents_changelog.0.1 context:dev,prod
--comment tb_documents creation tag: tb_documents_changelog.0.1

--
-- tb_documents
--

CREATE TABLE tb_documents(
                             id bigint primary key auto_increment,
                             title varchar(255) not null,
                             autor varchar(255) not null,
                             year integer not null,
                             presentation text not null,
                             url text,
                             image text,
                             document text,
                             ova_id bigint references tb_ovas(id)
);

--rollback DROP TABLE tb_documents

--changeset elvis.serrano:tb_feed_changelog.0.1 context:dev,prod
--comment tb_feed creation tag: tb_feed_changelog.0.1

--
-- tb_feed
--

CREATE TABLE tb_feed(
                             id bigint primary key auto_increment,
                             description text not null
);

--rollback DROP TABLE tb_feed

--changeset elvis.serrano:TB_EVENTS_changelog.0.1 context:dev,prod
--comment tb_events creation tag: tb_events_changelog.0.1

--
-- tb_events
--

CREATE TABLE tb_events(
                          id bigint primary key auto_increment,
                          description text,
                          location text not null,
                          date date not null,
                          time time not null
);

--rollback DROP TABLE tb_events

--changeset elvis.serrano:tb_subject_matter_changelog.0.2 context:dev,prod
--comment tb_subject_matter creation tag: tb_subject_matter_changelog.0.2

--
-- tb_subject_matter
--

ALTER TABLE tb_subject_matter ADD COLUMN title varchar(255);

--rollback DROP TABLE tb_subject_matter

--changeset elvis.serrano:tb_events_changelog.0.2 context:dev,prod
--comment tb_events creation tag: tb_events_changelog.0.2

--
-- tb_events
--

ALTER TABLE tb_events ADD COLUMN user_id bigint references tb_users(id);

--rollback DROP TABLE tb_events