# King of Bots(Android):联机在线AI对战游戏

*注：此文档是King of Bots网页端及后端的说明文档，后端及网页端见：[Li Feifei / King of Bots · GitLab ](https://git.acwing.com/Cassifa/king-of-bob)*

## 1.概述

​		**由贪吃蛇游戏改编而来，两名玩家在线联机对战，玩家可以选择指派自己设计的AI参与对战，或亲自出马。同时支持玩家赛后查看比赛回放**



## 2.项目介绍

​		**由贪吃蛇游戏改编而来，是一个前后端分离的游戏项目。游戏为两名玩家在线联机对战。由两个后端和两个前端，两个前端分别为Vue编写的网页端和原生安卓编写的客户端。两个后端分别为提供主服务的backendcloud和提供敏感词屏蔽功能的SensitiveWord。其中backendcloud由backend, MatchingSystem, BotRunningSystem 三个微服务组成**

   **比赛为在线对战回合制1v1游戏，玩家操控一条蛇躲避障碍物并设法堵住对方以取得胜利。玩家可以上传由java编写的Ai来代替自己出战并赢取天梯积分。支持赛后查看对局录像，并实时公布天梯排名。**

   **程序模拟了游戏引擎，对局所有元素继承自GameObject, 拥有start(), update(), destroy(), render()等多个函数，通过状态机记录游戏类，并且每秒刷新60次自身状态最终实现了游戏效果。**

​	**程序封装了数据持久层接口，使得访问与记录数据变得十分简便。玩家每次对战记录会被保存在自己本地数据库中供玩家分析也可在线查看所有的比赛对局回放。同时通过SharedPreferences来记录各项数据，实现了JWT验证登录、快捷登录、快速开启游戏、主题切换的功能。**

​	**程序封装了大量网络接口，屏蔽了各种数据不一致导致的问题，实现了前后端分离的效果，使得移动端的玩家可以和网页端玩家同台竞技。**

​	**在主要的游戏功能外海提供了一个敏感词屏蔽的功能，玩家输入文本后会自动过滤其中的隐私、身份信息和其它危险内容。通过调用ChatGPT接口实现此功能，APP调用backendcloud的接口，backendcloud再通过RestTemplate调用SensitiveWord接口，SensitiveWord通过https请求实现对ChatGPT的调用。**

​	**本APP还提供了切换主题色、快速开始游戏等功能**



### 2.1系统架构图

![][systemArchitecture]



### 2.2网络架构图

​	项目由四个后端服务，前端通过接口去后端Backend交互，Backend可调用BotRunningSystem、MatchingSystem、SensitiveWord提供的微服务执行特定任务

![][networdArchitecture]

### 2.3系统模块图

​	分为信息管理、游戏与回放、Bot管理、文本脱敏四大模块

![][moduleDiagram]



### 2.4安卓程序模块图

​	整个应用程序划分为4个模块，分别是用户界面、游戏运行模块、数据库模块、网络请求模块、后端（BackendCloud与SenstiveWord）

![][KOBAndroidRunningDiagram]

- ​	由模块结构图中可知，用户界面是整个应用程序的核心，主要包含四个子模块，1.回放模块，用于展示历史对局；2.敏感词模块，用于文本脱敏；3.排行榜模块，查看最新排行；4.Bot模块，用于对自己的Bot增删改查

- 游戏运行模块由匹配或回放启动，不断从用户界面或数据库获取数据并解析，实时渲染在画面上

- ​	网络请求模块屏蔽了大量的网络接口格式、鉴权、返回数据格式的问题，为用户界面提供方便的数据获取途径

- ​	后端由BackendCloud(含Backend、BotRunningSystem、MatchingSystem三部分)和SenstiveWord两个后端组成，Backend中会调用SenstiveWord接口，SenstiveWord再调用openai的接口为用户界面提供各种数据

- ​	数据库适配器封装了所有对SQLite数据库与SharePreference操作的方法，用户界面和游戏运行模块会调用它实现数据库和SharePreference操作



## 3.需求分析



## 4.UI界面展示

### 4.1匹配页面

​	登录后默认所在页面，玩家可以再次选择自己出战的Bot并开始游戏，也可亲自出马。开始匹配后会优先匹配天梯积分接近的玩家，若匹配过久也可能匹配到积分差距较大的对手。匹配成功后会展示对手身份信息并跳转到对局页面。

​	玩家进入页面即开启WebScoket连接与后端持续通信，直至此页面不可见时断开

![][mainView]



### 4.2对局页面

**效果：**

​	对局页面玩家如果是亲自出马对战就可以操作自己的游戏角色，每三回合蛇会伸长一格。玩家要在确保自身存货情况下堵住对方的去路以此取得胜利。对局结束后会把本次对局记录在本地数据库中供玩家查看。

​	玩家每次操作会通过WebScoket通知后端，后端也通过WebScoket连接返回对方的操作与本轮操作的结果

![][inPersonPlay]

**动画效果实现**

​	程序模拟了游戏引擎，游戏基类GameObject派生了Wall Snake GameMap三个类，GameObject中定义start(), update(), destroy(), render() 个私有函数，分别用于执行游戏类创建时、更新时、销毁时、渲染在画布时的行为。GameObject还有两个静态方法供TimeThread调用，每秒调用60次以更新和渲染游戏类

​	过重写start(), update(), destroy(), render()不同的类就能实现不同的行为与动画效果

​	GameObject由一个静态数组用于存放自身的所有实例，如wall、snake、gameMap

​	GameObject的两个静态方法分别为：step()和rendrAll()。

- step()会更新一次所有数组中的实例状态

- renderAll会把根据实例当前状态把所有实例全部渲染一次

```java
    public static void step(long timeStamp) {
        for (GameObject now : list) {
            if (!now.hasCalled) {
                now.hasCalled = true;
                now.start();
            } else {
                now.timeDelta = timeStamp - lastTimeStamp;
                now.update();
            }
        }
        GameObject.lastTimeStamp = timeStamp;
    }


    public static void renderAll(Canvas canvas) {
        //保证蛇在顶层
        for (GameObject now : GameObject.list)
            if (!(now instanceof Snake))
                now.render(canvas);
        for (GameObject now : GameObject.list)
            if (now instanceof Snake)
                now.render(canvas);
    }

```

**贪吃蛇运动：**

​	小蛇由一串联通的canvas画出的正方形构成，头尾为圆形，头部有眼睛作标识。继承自游戏基类，每秒刷新60次，每次清除之前图像再渲染新图像以达到动画效果。每次移动会向前推进头尾格



### 4.3排行榜页面

​	系统会根据玩家的天梯积分为所有玩家排名，初始默认积分1500，每次获胜+5，失败-3（游戏操作步数小于5步的对局将被忽略）。玩家通过创造更强的Bot来达到更高的排名。

​	此处展示玩家排名、玩家积分、玩家头像、玩家昵称。数据通过访问getRankList结果获取，再根据RankItemAdapter与条目适配后呈现。

![][rankList]



### 4.4全局回放页面

​	此页面展示所有有被记录的对局，玩家点击即可查看对应比赛的回放。本页面与“我的回放页面”通过TabView与ViewPage2实现了滑动切换的效果

​	此处展示对局的两名玩家头像、昵称与对局时间。数据通过访问getRecordList结果获取，再根据RecordItemAdapter与条目适配后呈现。

![][allrecord]



### 4.5我的回放页面

​	此页面展示当前登录玩家的在此设备的游戏记录，玩家可以查看回放或获取数据库数据做分析用以改进自己的Bot代码

![][personalRecord]



### 4.6对局回放页面

​	玩家在“全局回放页面”或“我的回放页面”点击对局条目后可跳转到此页面。此页面会解析回放数据并在模拟的游戏引擎中依次执行以达到重现对局的效果

​	如果是从“全局回放页面”进入则数据通过网络请求获取。如果是通过“我的回放页面”进入则数据通过访问SQLite获取

![][showRecord]



### 4.7个人信息页面

​	此页面展示用户信息，包括头像，昵称，天梯积分等信息，此页面也是跳转“我的Bot” 页面和“敏感词检测”页面的入口。用户也能在此页面切换主题或是退出登录。

​	数据通过访问getInfo接口获取

![][userInfo]



### 4.8我的Bot页面

​	此页面展示用户所有Bot列表，展示的信息包括Bot名称、创建时间。用户也可以修改、删除、新建Bot。

​	数据通过getBotList接口走网络请求获取

![][botList]



### 4.9修改、添加Bot页面

​	增删改分别走对应的接口，此处展示Bot的名称、简介与代码内容。其中代码内容为空，如果没有填写代码则无法新建Bot

![][newBot]

![][editBot]



### 4.10敏感词检测页面

**效果：**

​	玩家输入文本后会自动过滤其中的隐私、身份信息和其它危险内容。通过调用ChatGPT接口实现此功能，APP调用backendcloud的接口，backendcloud再通过RestTemplate调用SensitiveWord接口，SensitiveWord通过https请求实现对ChatGPT的调用。还提供了一键复制脱敏文本的功能。

**实现：**

​	APP调用backendcloud的接口，backendcloud再通过RestTemplate调用SensitiveWord接口，SensitiveWord通过https请求实现对ChatGPT的调用。Token是从OpenAI购买的

![][block]

### 4.11登录页面

​	本项目的登录模式为JWT鉴权登录。Token是用户的凭证。此页面要求玩家输入账号密码，确认登录会走login的接口，若后端认定登录成功会分配一个token，后续token是用户的唯一凭证，token过期后强制下线重新登录。

- 登录成功后会通过SharedPreferences记录数据，后续用户点击即可进入游戏，无需再次登录。

- 玩家密码在后端是加密存储的，每次登录判断用户输入密码与加密密码是否对应。

- 登录成功后会情况当前Activity栈并跳转到主界面，用户不退出登录无法回到此页面。

![][login]

- 退出登录

![][logout]

### 4.12注册页面

​	类似登录页面，用户输入昵称、密码、确认密码后若昵称无重复则注册成功。注册成功后会自动登录，用户无需再额外操作。

![][register]

### 4.13主题切换效果

**效果：**

​	APP提供了两种主题：红色与蓝色，在Application初始化时加载本次主题，用户切换主题后再次打卡APP即可体验新主题。

**实现：**

​	用户切换主题后会修改SharePreference中记录，每次启动Applacation时提前设置主题

- 主题切换后效果

![][changeStyleSelect]

![][changeStyle]



### 4.14快速开始游戏图标

**效果：**

​	本页面玩家长按图标即可启动，也可以将快捷方式注册在页面上即可一次点击启动。点击后若token未过期则直接登录并且派上一次对战的Bot出战，若为第一次游戏则亲自出马

**实现：**

​	快速开始对战会携带intent进入MainActivity, MainActivity解析intent并传递给playGroundFragment，同样playGroundFragment也会先解析intent, 如果为快速开始则直接读取SharePreference数据并开始匹配

![][fastStart]



## 5.安卓数据库设计

本应用主要存储两种数据：

1. 配置信息：因为配置信息的数据量很小，从Android支持的存储方式上分析，可以保存在SharePreference、文件或SQLite数据库中

1. 本地回放信息：回放条目数据量会随着用户的使用而逐渐增多，且格式固定所以用SQLite存

### 5.1 SharePreference

​	配置信息中主要保存用户数据（昵称、头像、积分、id）、token、当前主题、上次出战BotId(默认-1)

- 配置信息的数据库表结构

| **属性** | **数据类型** | **说明**           |
| -------- | ------------ | ------------------ |
| userName | string       | 用户昵称           |
| photo    | string       | 用户头像           |
| rating   | integer      | 天梯积分           |
| id       | integer      | 玩家Id             |
| theme    | string       | 当前主题，默认蓝色 |
| botId    | integer      | 上次出战Bot Id     |
| token    | string       | JWT登录凭证        |

### 5.2 SQLite

​	回放服务保存了两位玩家的昵称、头像、对局结果以及JSON格式下的回放数据。

​	JSON格式回放数据中由包含了两位玩家的起始id, 玩家操作序列、地图等内容，游戏运行模块通过解析反序列化后的record按操作一步步移动即可展示对应的历史对局

- 玩家对局回放的数据库表结构

| **属性**   | **数据类型** | **说明**           |
| ---------- | ------------ | ------------------ |
| a_photo    | string       | 玩家a的头像        |
| a_username | string       | 玩家a的昵称        |
| b_photo    | string       | 玩家b的头像        |
| b_username | string       | 玩家b的昵称        |
| record     | string       | JSON格式的回放信息 |
| result     | string       | 对局结果           |



## 6.网络模块封装

### 6.1集成hilt

​	封装了hilt, 需要使用时通过@Inject注入。在发送请求前通过请求拦截器在请求头加入当前token

- 网络包

```java
package com.example.kob_android.net.hilt;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.kob_android.net.ApiService;
import com.example.kob_android.net.BotApiService;
import com.example.kob_android.net.ListApiService;
import com.example.kob_android.utils.Constant;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  04:59
 * @Description:
 */
@InstallIn(ApplicationComponent.class)
@Module
public class NetWorkModule {
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(@NonNull String s) {
                Log.i("aaa", "请求日志" + s);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {//自己会找上面的
        return new Retrofit.Builder().baseUrl("http://" + Constant.getBackIpAddress() + ":3000/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .build();
    }

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    BotApiService provideBotApiService(Retrofit retrofit) {
        return retrofit.create(BotApiService.class);
    }

    @Singleton
    @Provides
    ListApiService providePKApiService(Retrofit retrofit) {
        return retrofit.create(ListApiService.class);
    }
}

```

- 通过请求拦截器为请求添加token

```java
package com.example.kob_android.net.hilt;

import androidx.annotation.NonNull;

import com.example.kob_android.database.UserSharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  05:22
 * @Description:
 */
public class TokenInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // 添加 Authorization 头部
        Request.Builder requestBuilder = originalRequest.newBuilder();
        if (UserSharedPreferences.getInstance().getToken() != null)
            requestBuilder.header("Authorization", "Bearer " + UserSharedPreferences.getInstance().getToken());

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}

```



### 6.2JWT验证登录

​	每次APP打开会尝试通过SharePreference保存的token请求个人信息，如果没有token或请求失败则要求重新登录，否则直接进入MainActivity, 跳过LoginActivity。

​	注册成功后会携带intent跳转LoginActivity, 在LoginActivity中判断是否有注册页面传来的intent，如果有则直接执行登录逻辑

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果是从注册跳转来的
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("username") != null) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            if (username != null && password != null) {
                tryLogin(username, password);
            }
        } else {
            //使用存储数据尝试登录
            if (UserSharedPreferences.getInstance().getToken() != null) {
                updateUserInfo();
            } else {
                //没有token
                setContentView(R.layout.activity_login);
                initView();
            }
        }
    }
```



## 7.文件结构

### 7.1安卓代码部分

- King of Bots源代码文件结构

![][codeFileStructure]

- 文件用途说明

| 包名称                 | 文件名                | 说明                             |
| ---------------------- | --------------------- | -------------------------------- |
| kob_android.Activity   | LoginActivity         | 登录页面                         |
| kob_android.Activity   | MainActivity          | 主页面，几乎所有Fragment挂靠     |
| kob_android.Activity   | ModifyMyBotActivity   | 修改Bot的界面                    |
| kob_android.Activity   | RegisterActivity      | 注册界面                         |
| kob_android.Activity   | SenstiveWordActivity  | 敏感词屏蔽界面                   |
| kob_android.Activity   | ShowRecordActivity    | 播放回放页面                     |
|                        |                       |                                  |
| kob_android.adapter    | ViewPagerAdapter      | Page适配器                       |
| kob_android.adapter    | BotItemAdapter        | Bot条目适配器                    |
| kob_android.adapter    | RankItemAdapter       | 排行条目适配器                   |
| kob_android.adapter    | RecordItemAdapter     | 回放条目适配器                   |
|                        |                       |                                  |
| kob_android.database   | RecordItemDBHelper    | 封装SQLite                       |
| kob_android.database   | UserSharedPreferences | 封装SharedPreferences            |
|                        |                       |                                  |
| kob_android.Fragment   | AllRecordFragment     | 展示所有回放的Fragment           |
| kob_android.Fragment   | MatchFragment         | 匹配组件的Fragment               |
| kob_android.Fragment   | MyRecordFragment      | 展示我的回放Fragment             |
| kob_android.Fragment   | UserActionFragment    | 用户移动蛇的Fragment             |
| kob_android.Fragment   | BotListFragment       | Bot列表的Fragment                |
| kob_android.Fragment   | PlayGroundFragment    | 游戏页面的Fragment               |
| kob_android.Fragment   | RankListFragment      | 排行页面的Fragment               |
| kob_android.Fragment   | RecordListFragment    | 回放列表的Fragment               |
| kob_android.Fragment   | UserInfoFragment      | 用户信息的Fragment               |
|                        |                       |                                  |
| kob_android.gameObject | Cell                  | 蛇身体单元                       |
| kob_android.gameObject | GameMapInfo           | 当前对局信息                     |
| kob_android.gameObject | SnakeInfo             | 蛇的配置信息                     |
| kob_android.gameObject | StartGameInfo         | 开启游戏时初始数据               |
| kob_android.gameObject | GameMap               | 游戏地图配件                     |
| kob_android.gameObject | GameObject            | 游戏基类，所有游戏类继承此类     |
| kob_android.gameObject | MySurfaceView         | 游戏界面类，展示游戏动画         |
| kob_android.gameObject | Snake                 | 蛇类，执行蛇行为的发出者         |
| kob_android.gameObject | TimeThread            | 线程类，配合界面类控制所有游戏类 |
| kob_android.gameObject | Wall                  | 墙类                             |
|                        |                       |                                  |
| kob_android.net        | NetWorkModule         | 网络配置文件                     |
| kob_android.net        | TokenInterceptor      | 为所有请求添加token              |
| kob_android.net        | RankListData          | 排行条目数据格式适配器           |
| kob_android.net        | RecordItemsData       | 回放条目适配器                   |
| kob_android.net        | ApiService            | 一系列接口api                    |
| kob_android.net        | BotApiService         | 一系列接口api                    |
| kob_android.net        | ListApiService        | 一系列接口api                    |
|                        |                       |                                  |
| kob_android.pojo       | Bot                   | Ai类                             |
| kob_android.pojo       | Record                | 回放类                           |
| kob_android.pojo       | RecordItem            | 回放条目适配器                   |
| kob_android.pojo       | User                  | 用户类                           |
|                        |                       |                                  |
| kob_android.utils      | Constant              | 工具类                           |
|                        |                       |                                  |
| kob_android            | MyApplication         | Application,APP初始化工具        |



### 7.2资源文件部分

- King of Bots资源与配置文件文件结构

![][resourceFileStructure]

- 文件用途说明

​	资源过多，这里只详细介绍layout目录下的

| **资源目录名** | **作用**                             |
| -------------- | ------------------------------------ |
| drawable       | 可绘制组件，主要用来实现登录页面效果 |
| layout         | 布局文件                             |
| mipmap         | 图片资源，存启动图标与快速开始图标   |
| values         | 字符串、颜色、值资源，供其它地方引用 |
| xml            | 快速启动配置文件                     |

​	Layout资源资源目录:

| **资源名称**            | **功能**                |
| ----------------------- | ----------------------- |
| activity_login          | 登录页面资源文件        |
| activity_main           | 主页面资源文件          |
| activity_modifybot      | 编辑Bot页面资源文件     |
| activity_register       | 注册页面资源文件        |
| activity_sensitivewords | 敏感词页面资源文件      |
| activity_show_rocord    | 回放页面资源文件        |
| fragment_match          | 匹配页面组件资源文件    |
| fragment_mybots         | 我的Bot页面组件资源文件 |
| fragment_playground     | 游戏页面组件资源文件    |
| fragment_ranklist       | 排行页面组件资源文件    |
| fragment_record         | 回放页面组件资源文件    |
| fragment_userinfo       | 用户信息组件资源文件    |
| fragment_user_action    | 用户移动组件资源文件    |
| item_bot                | Bot条目资源文件         |
| item_rank               | 排行条目资源文件        |
| item_record             | 回放条目资源文件        |
| sub_fragment_all_record | 所有回放条目资源文件    |
| sub_fragment_my_record  | 我的回放条目资源文件    |

 



## 8.技术支持

### 8.1开发工具

开发工具：Android Studio 2023.1.1

版本管理工具：Git



### 8.2安卓SDK及虚拟机环境

SDK:安卓SDK34

虚拟机：Medium Phone API 34









[allrecord]: ./desgin/img/allrecord.jpg
[APPlogo]: ./desgin/img/APPlogo.png
[block]: ./desgin/img/block.png
[botList]: ./desgin/img/botList.jpg
[changeStyle]:  ./desgin/img/changeStyle.jpg
[changeStyleSelect]:  ./desgin/img/changeStyleSelect.jpg
[codeFileStructure]:  ./desgin/img/codeFileStructure.png
[editBot]:  ./desgin/img/editBot.jpg
[fastStart]:  ./desgin/img/fastStart.png
[inPersonPlay]:  ./desgin/img/inPersonPlay.jpg
[KOBAndroidRunningDiagram]:  ./desgin/img/KOBAndroidRunningDiagram.png
[login]:  ./desgin/img/login.jpg
[logout]:  ./desgin/img/logout.jpg
[mainView]:  ./desgin/img/mainView.jpg
[moduleDiagram]:  ./desgin/img/moduleDiagram.png
[networdArchitecture]:  ./desgin/img/networdArchitecture.png
[newBot]:  ./desgin/img/newBot.jpg
[personalRecord]:  ./desgin/img/personalRecord.png
[rankList]:  ./desgin/img/rankList.jpg
[register]:  ./desgin/img/register.jpg
[showRecord]:  ./desgin/img/showRecord.jpg
[systemArchitecture]:  ./desgin/img/systemArchitecture.png
[userInfo]: ./desgin/img/userInfo.jpg
[resourceFileStructure]: ./desgin/img/resourceFileStructure.png