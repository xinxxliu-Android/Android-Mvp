####    项目简介    ####
#1、项目架构
    本项目 使用 MVP架构。 使用Module按照功能进行模块化划分
#2、项目结构
    本项目为 飞巡、渝智巡 等全部app应用的集合源码项目。
    项目划分，使用 /config.gradle 中 android.CHANNEL_KEY (当前第54行) 进行地区限定加载不同应用名、包名等
    加载不同资源的方式为：Module名(如 app,ModuleMain等)/channelConfig.gradle配置 如 app/channelConfig.gradle第7-70行,可根据具体需求进行调整
#3、其他说明
    当前项目各说明文档皆在当前目录平级文件中。
    进行开发工作前，务必仔细阅读：
    1、项目结构目录
    2、项目开发规范
    3、各Module模块中READ_ME.md说明文档
    4、项目依赖配置抽象说明
    5、各Module模块中package-info.java
    5、其他中文命名文档及各Module模块中中文命名说明文档
#4、其他说明文档待慢慢完善。

https://github.com/pedroSG94/rtmp-rtsp-stream-client-java
推流依赖