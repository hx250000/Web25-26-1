DROP TABLE IF EXISTS user;

create table user
(
    id           bigint auto_increment primary key,
    userName     varchar(256)                       null comment '用户名',
    userPassword varchar(512)                       null comment '密码',
    updateTime   datetime default CURRENT_TIMESTAMP null,
    createTime   datetime default CURRENT_TIMESTAMP null,
    isDelete     tinyint                            null,
    userRole     int      default 0                 null comment '表示用户角色， 0 普通用户， 1 管理员'
)
    comment '用户表';

insert into `user` (userName,userPassword,isDelete,userRole) values
    ('testUser1','testPassword1',0,0),
    ('testUser2','testPassword2',0,0);
