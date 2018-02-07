[![Platform][1]][2]  [![Build Status][3]][4]  [![Release][5]][6]  [![GitHub license][7]][8]  [![Downloads][9]][10]

[1]:https://img.shields.io/badge/platform-Android-blue.svg
[2]:https://github.com/iMeiji/Daily
[3]:https://travis-ci.org/iMeiji/Daily.svg?branch=master
[4]:https://travis-ci.org/iMeiji/Daily
[5]:https://img.shields.io/github/release/iMeiji/Daily.svg
[6]:https://github.com/iMeiji/Daily/releases/latest
[7]:https://img.shields.io/badge/license-Apache%202-blue.svg
[8]:https://github.com/iMeiji/Daily/blob/master/LICENSE
[9]:https://img.shields.io/github/downloads/iMeiji/Daily/total.svg?maxAge=1800
[10]:https://github.com/iMeiji/Daily/releases


## Daily
第三方[知乎专栏](https://zhuanlan.zhihu.com/)Android App，风格采用了Material Design，有 Kotlin 和 Java 两个版本，切换分支即可查看




### 代码特性
- 使用 Google I/O'17 发布的 [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) 架构组件
- 使用 Lifecycle，LiveData，ViewModel 以及 Room，非常优雅的让数据与界面交互，并做一些持久化的东西，高度解耦，自动管理生命周期，而且不用担心内存泄漏的问题
- 代码入手难度极低，源于对于"简单直观、干净清晰"理念的把握和追求
- 基于 [MultiType](https://github.com/drakeet/MultiType)，全局没有新创建任何一个 Adapter 类
- 模块清晰，聚合有度
- 使用最新 RxJava 2，Glide 4 新特征
- 使用 DiffUtil 优雅实现 notifyDataSetChanged
- 运用 Kotlin 语法糖，精简代码




### 已实现的功能
- 专栏内容阅读

- 添加自定义专栏

- 删除自定义专栏

- 自定义主题颜色

- 仿知乎动态切换夜间模式（无需recreate）

- 集成 Farbic 自动上传奔溃 log，方便开发者发现 bug

  ​



### 待实现的功能
- 收藏夹

- 清除缓存

  ​



### 更新日志
```
2018-2-7
完成 Kotlin 版本

2017-12-17
更新 RxJava 2，Glide 4
集成 Fabric SDK 

2017-12-7
使用 Android Architecture Components 架构，移除 MVP 架构，告别繁琐的接口调用
使用 Room 操作 SQL

2017-7-13
添加夜间模式(仿知乎)

2017-7-11
引入 Dagger2 

2017-6-8
封装加载更多, Diff等

2017-6-7
引入 MultiTypeAdapter, DiffUtil

2017-5-5
引入 RxLiftcycle

2017-5-2
增加缓存机制
Gradle 差异化构建

2017-4-25
引入 Retrofit, RxJava

2017-4-22
重启项目
```



### API

[数据来源](https://github.com/shanelau/zhihu)  



### 截图

<img src="/screenshots/daily_1.gif" width="360" height="640"/>
<img src="/screenshots/daily_2.gif" width="360" height="640"/>
<img src="/screenshots/daily_3.gif" width="360" height="640"/>
<img src="/screenshots/daily_4.gif" width="360" height="640"/>



### 下载

[GitHub Release](https://github.com/iMeiji/Daily/releases)



### 编译

1. 导入项目
2. 复制 `fabric.properties.example` 并重命名为 `fabric.properties`
3. 运行项目




### 开源库

- [Android Support Libraries](https://developer.android.com/topic/libraries/support-library/index.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [OkHttp](https://github.com/square/okhttp)
- [Gson](https://github.com/google/gson)
- [Glide](https://github.com/bumptech/glide)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [LicensesDialog](https://github.com/PSDev/LicensesDialog)
- [Retrofit](https://github.com/square/retrofit)
- [PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [MultiType](https://github.com/drakeet/MultiType)
- [Dagger 2](https://github.com/google/dagger)

