## core

### 项目介绍
该项目为框架核心包，用于控制使用开源框架的版本和提供通用工具类。

### 软件架构
- JDK: 1.8.X
- Spring Boot: 2.1.5.RELEASE
- MySQL: 5.7.X
- Lombok: 1.16.22


### 版本说明

#### 2.0.0 - build
- resources模块后期将会删除，实现前后端分离

#### 1.0.0 
- annotation: 关于注解的封装
- base: 基本的Entity(AbstractAttribute-基础标签实体)/Repository/Service/Controller
- business: 较为复杂的Repository，例如AbstractBusinessRepository，不限制实体类型，可以随意跨实体进行查询
- common: 工具类，枚举类和EnumVO
- error: 异常页面定义
- log：日志注解，以及默认打印Controller和RestController的日志
- script: 脚本执行器，用于实现每个系统的脚本自动执行功能
- resources：提供前端静态资源以及脚本
