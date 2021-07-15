环境配置：
1、安装JDK1.8
2、安装MySQL
3、安装Redis（集群）

MySQL数据库：
CREATE TABLE `vote` (
  `id` bigint(16) NOT NULL COMMENT '选举主键',
  `name` varchar(255) NOT NULL COMMENT '选举名字',
  `status` int(1) NOT NULL COMMENT '状态：0：未开始，1：已开始，2：已结束',
  `is_del` int(1) NOT NULL COMMENT '是否删除：0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选举表';

CREATE TABLE `vote_candidate` (
  `id` bigint(16) NOT NULL COMMENT '选举候选人id',
  `vote_id` bigint(16) NOT NULL COMMENT '选举id',
  `id_card` varchar(20) NOT NULL COMMENT '身份证',
  `name` varchar(100) NOT NULL COMMENT '名字',
  `poll` int(10) NOT NULL DEFAULT '0' COMMENT '票数',
  `is_del` int(1) NOT NULL COMMENT '是否删除：0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `vote_id` (`vote_id`,`id_card`) USING BTREE COMMENT '选举id和身份证号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选举候选人表';

CREATE TABLE `vote_record` (
  `id` bigint(16) NOT NULL COMMENT '选举记录id',
  `vote_id` bigint(16) NOT NULL COMMENT '选举id',
  `user_id` bigint(16) NOT NULL COMMENT '用户id',
  `candidate_id` bigint(16) NOT NULL COMMENT '候选人id',
  `is_del` int(1) NOT NULL COMMENT '是否删除：0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `vote_id` (`vote_id`,`user_id`) USING BTREE COMMENT '选举id',
  KEY `candidate_id` (`candidate_id`) USING BTREE COMMENT '候选人id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投票记录表';

CREATE TABLE `vote_user` (
  `id` bigint(16) NOT NULL COMMENT '用户id',
  `id_card` varchar(20) NOT NULL COMMENT '身份证',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `is_del` int(1) NOT NULL COMMENT '是否删除：0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `id_card` (`id_card`) USING BTREE COMMENT '身份证索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选举用户表';

启动项目步骤：
1、修改application-dev.yml中的datasource配置、redis配置(redis为集群配置)
2、所在的MySQL数据库执行上面的SQL语句
3、启动VoteApplication
4、启动成功之后，有个swagger地址，此为接口文档，可直接进行接口测试
5、需要先执行系统管理后台的/addVote接口
6、此后按测试项目的需求运行
