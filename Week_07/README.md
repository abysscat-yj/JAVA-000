#### 一、按自己设计的表结构，插入 100 万用户模拟数据，测试不同方式的插入效率

一共测试六种方式：

##### 1. PrepareStatement addBatch 开启自动提交事务
```
time: 3302502ms = 3302s = 55min
```
##### 2. PrepareStatement addBatch 关闭自动提交事务，单个大事务
```
time: 195965ms = 196s = 3.26min
```
##### 3. PrepareStatement addBatch 关闭自动提交事务，5000条/事务
```
time: 157209ms = 157s = 2.6min
```
##### 4. PrepareStatement 将100w数据拼接为 单 条SQL执行
```
time: 19033ms = 19s
```
##### 5.PrepareStatement 将100w数据拼接为 多 条SQL执行
```
time: 20s
```
##### 6.PrepareStatement addBatch 关闭自动提交事务，5000条/事务，jdbc-url添加：rewriteBatchedStatements=true
```
time: 13s
```
注：addBatch 默认是没有开启真正批处理的，只有开启rewriteBatchedStatements后，jdbc才会拼接sql一次打包请求


#### 二、读写分离 - 动态切换数据源版本 1.0

#### 三、读写分离 - 数据库框架版本 2.0
