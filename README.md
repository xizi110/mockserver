# 模拟服务器
> 和第三方对接接口时，第三方接口还未开发完成，有时服务器都没有提供，
> 本地开发后就可以使用模拟服务器模拟第三方接口返回数据。
> 不含具体业务逻辑，只是简单的模拟接口返回数据。并且增加简单的接口匹配条件。

**程序配置文件**
```yaml
server:
    port: 8081
    servlet:
        context-path: /mock
# 接口地址
interfaces:
  location: interfaces
```
interfaces下为接口yml文件，一个接口一个文件。


**yml接口文件格式**
```yaml
description: "测试,成功，返回200" # 接口描述
url: "/test" # 接口路径
method: "GET" # 请求方法
request:
  headers: # 请求头
#    - "header1=value1" # header值必须等于value1的请求才会被匹配，多个条件为and关系
#    - "header2!=value2" # header值不等于value2（包括header不存在）的请求才会被匹配
  parameters: # 请求参数
#    - "param1=value1" # param1值必须等于value1的请求才会被匹配，多个条件为and关系
#    - "param2!=value2" # param2值不等于value2（包括param2不存在）的请求才会被匹配
response:
  headers: # 响应头
    - name: "content-type" # 响应头的名称
      value: "application/json" # 响应头的值
  content: "{\"status\":\"success\",\"code\":200}" # 响应内容
```
两个完全一样的接口会启动报错。