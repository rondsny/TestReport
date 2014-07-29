TestReport 是一个用于爬取 redmine 系统bug缺陷并生成测试报告数据的JSP web应用。

resource 目录下的config.propertise 用于保存相关配置。在部署时需要完成以下配置：

PT_URL =http://dns.freemine.com

MAIL_HOST =mail.test.com

MAIL_FROM =name1@test.com

MAIL_TOS =name2@test.com|name3@test.com

否则，无法正常运行TestRepot。

TestReport 推荐部署在Tomcat 下。

如有相关疑问或建议，随时可以在下面评论反馈。
