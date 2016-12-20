# Translator
A basic android app architecture

okhttp3
gson
butterknife
glide
event-bus

Package List:
App: Base components of App, Such as Activity, Fragment, Application, etc

Data: Const data
    -HttpRequestPool: All the http request will be list here
Net: Http related, we use “httpOk” here to handle the http request.

Utils: Log, Toast, Dialog etc
    -image: To handle the display and loading of picture by Glide

View: We can divid the whole app into some blocks by UI, such as main, help, settings,etc, each component should be put into this folder


ToDo List:
网络 doing
网络层封装 doing
图片 doing
常用控件（下拉刷新，List）
事件传递 done
依赖注入 done
内存泄漏检查
测试框架
Log打印 doing
错误页面的统一处理
Json done
缓存
公用信息（Dialog，alert）
埋点
Push
Activity,Fragment基础父类
地图
登录
热修复
Bugly
数据存储

分包原则
代码风格
