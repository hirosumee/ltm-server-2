create database new_database collate SQL_Latin1_General_CP1_CI_AS
go

create table Room
(
    id          int identity
        constraint Room_pk
            primary key nonclustered,
    group_IP    nvarchar(15),
    type        nvarchar(10),
    creator     nvarchar(255) not null,
    time        date,
    update_time date
)
go

create unique index Room_id_uindex
    on Room (id)
go

create table [User]
(
    username nchar(255) not null
        constraint PK_User
            primary key,
    password nchar(255) not null
)
go

create table [File]
(
    id        int identity
        constraint File_pk
            primary key nonclustered,
    name      nchar(255),
    size      int,
    time      date,
    mimetype  nchar(10),
    file_path nchar(255),
    uploader  nchar(255) not null
        constraint fk_File_uploader
            references [User]
)
go

create unique index File_id_uindex
    on [File] (id)
go

create table [Join]
(
    id       int identity
        constraint Join_pk
            primary key nonclustered,
    username nchar(255)
        constraint FK_Joint_User
            references [User],
    time     date,
    id_room  int
        constraint Join_Room
            references Room,
    adder    nchar(255)
)
go

create unique index Join_id_uindex
    on [Join] (id)
go

create table MessageFile
(
    id       int identity
        constraint MessFile_pk
            primary key nonclustered,
    id_join  int
        constraint MessFile___fk_Join
            references [Join],
    time     date,
    id_file  int not null
        constraint MessFile___fk_File
            references [File],
    id_room  int not null,
    username nchar(255)
        constraint MessageFile___fk_user
            references [User]
)
go

create unique index MessFile_id_uindex
    on MessageFile (id)
go

create table MessageText
(
    id       int identity
        constraint MessageText_pk
            primary key nonclustered,
    id_room  int        not null,
    id_join  int        not null
        constraint MessageText___fk_join
            references [Join],
    content  nvarchar(300),
    time     date,
    username nchar(255) not null
        constraint MessageText___fk_user
            references [User]
)
go

create unique index MessageText_id_uindex
    on MessageText (id)
go


