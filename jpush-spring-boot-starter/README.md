# 极光推送服务端辅助工具包

## 概述
包含极光推送工具服务端SDK一些常用的配置和逻辑。可直接快速集成到您的 SpringBoot 项目中。支持的功能包括：
- 根据设备 Registration ID 推送
- 根据别名推送
- 根据标签推送
- 推送给全部客户端设备
- 定时推送给全部客户端设备
- 定时发送消息
- 查询指定别名下的设备 Registration ID
- 删除指定别名
- 自定义代理服务器
- 失败重试
- 指定角标
- 指定优先级
- 传递自定义业务参数

## 特别说明
本工具针对官方 SDK 进行封装，支持常用的几项功能，并未覆盖 `SDK` 中全部功能。您可以自行扩展。

## 配置说明
### 默认
当主机可以直接访问 api.jpush.cn 时，采用如下配置：
```yaml
push:
  app-key: 123456789
  master-secret: 123456789
```
### 使用代理服务器
对于某些主机无法直接访问 api.jpush.cn 的情况，可以配置代理服务器来实现连接。
#### 使用公开代理
```yaml
push:
  app-key: 123456789
  master-secret: 123456789
  use-proxy: true
  proxy-host: 192.168.2.2
  proxy-port: 3128
```
#### 使用需认证的代理
```yaml
push:
  app-key: 123456789
  master-secret: 123456789
  use-proxy: true
  proxy-host: 192.168.2.3
  proxy-port: 3127
  proxy-username: proxy
  proxy-password: 123456
```
### 开启重试
可自行配置重试时间间隔和重试次数：
```yaml
  retry-interval: 1000
  retry-max-attempts: 3
```
说明：
- retry-interval 表示第1次重试的时间间隔，单位为毫秒；如果 retry-max-attempts 值大于1，则从第2次重试开始，重试间隔时间逐次翻倍；
- retry-max-attempts=0 时表示不做重试，默认值为0。

## 使用范例
### 引入依赖
```xml
<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>jpush-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
```
### 注入
```java
@Resource
private PushConnection pushConnection;
```
### 设置消息属性
```java
PushMessage pm = new PushMessage();

// 消息内容
pm.setContent("这是一条测试消息！");

// 指定角标值(仅在iOS设备生效)
pm.setBadge(8);

// 指定优先级(仅在安卓设备生效)
pm.setPriority(1);

// 指定推送标题
pushMessage.setTitle("墨白君推送！！！");

// 附加业务参数
Map<String, String> extras = new HashMap<>();
extras.put("bizCode", "123");
pm.setExtras(extras);
```
### 根据设备Registration ID推送
```
List<String> deviceIdList = new ArrayList<>();
deviceIdList.add("asdfghjkl");
deviceIdList.add("lkjhgfdsa");
Long msgId = pushConnection.pushToDevices(deviceIdList, pm);
```
### 根据别名推送
```java
List<String> aliasList = new ArrayList<>();
aliasList.add("3ab016d0");
aliasList.add("7e9e2382");
Long msgId = pushConnection.pushToAliases(aliasList, pm);
```
### 根据标签推送
```java
List<String> tagsList = new ArrayList<>();
tagsList.add("test-tag1");
Long msgId = pushConnection.pushToTags(tagsList, pm);
```
### 推送给全部客户端设备
```java
Long msgId = pushConnection.pushToAll(pm);
```
### 查询指定别名下的设备Registration ID
```java
List<String> regIdList = pushConnection.findRegistrationId("3ab016d0");
```
### 删除指定别名

```java
List<String> toDelete = new ArrayList<>();
toDelete.add("3ab016d0");
toDelete.add("7e9e2382");
pushConnection.deleteAlias(toDelete);
```

### 定时推送消息

~~~java
// 指定推送客户端
pushConnection.sendToSchedule(pm, LocalDateTime.now(), Audience.all());
// 默认推送全部用户
public boolean pushToSchedule(PushMessage pm, LocalDateTime time)；
~~~

