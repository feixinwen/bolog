-- 双减号是注释符
-- 通过这文件来编写建库建表的 sql

-- 如果 java_blog 数据库不存在，则创建把它创建出来；反之不创建。
create database if not exists java_blog;

-- 选中数据库
use java_blog;
-- 此时我们已经进入 数据库java_blog内部了

-- 下面我们来创一个博客表
-- 在创建之前，先判断一下数据库中是否已经存在 blog 数据表
-- 如果存在，删除它
drop table if exists blog;
create table blog(
    -- 将博客序号设置为一个自增主键
                     blogId int primary key auto_increment,
    -- 博客标题
                     title varchar(1024),
    -- 博客正文：注意博客内容是非常长的，因此varchar是不够的，故使用 mediumtext.
    -- mediumtext 可以表示几个G 的数据。大概16兆
                     content mediumtext,

    -- 博客作者ID
                     userId int,

    -- 文章的发布时间
                     postTime datetime
);

insert into blog values (null,"这是我的第一篇博客","正文：昨天要搞事情！",1,now());
insert into blog values (null,"这是我的第一篇博客","正文：今天我还要搞事情！",1,now());
insert into blog values (null,"这是我的第一篇博客","正文：明天我一定要搞事情！",1,now());


-- 用户表
drop table  if exists user ;
create table user(
                     userId int primary key auto_increment,
    -- 去重
                     username varchar(128) unique,
                     password varchar(128)
);
-- 因为 用户id 为主键，直接置为null，默认从1开始分配编号
-- userId=1
insert into user values(null,"zhangsan","123");
-- userId=2
insert into user values(null,"lisi","456");
