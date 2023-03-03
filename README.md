# wallet-demo
## Demo背景
电商业务中，需要给电商app设计一个用户钱包...


用程序实现如下api接口: 
1.  查询用户钱包余额
2. 用户消费100元的接口
3. 用户退款20元接口
4. 查询用户钱包金额变动明细的接口

## 采用技术
- SpringBoot 2.7.3
- MySQL 8
- MyBaits 3.5.9
- hutool-captcha 5.8.12（雪花算法生成）

## 解决思路
创建三个数据表:
- balance
- user
- order

balance表与user表对应, 充当用户钱包, balance 表中的可用余额为真实余额。 
每次操作不直接修改用户的余额， 通过总余额与冻结金额进行生成。
对用户的消费，退款等操作，都是执行冻结金额的SQL操作，每次操作根据更新的冻结金额进行可用余额的结算。

user表保存用户的信息以及对应的钱包id（balance_id），只需要获取到用户的id即可进行相应操作，出于简单考虑，Demo中没有加入权限系统，未进行任何认证和授权。

order表，order表用于记录每次用户消费，记录用户id，消费的商品，消费的总额，消费时间， 是否为退款订单以及用雪花生成的订单序列号。
本人考虑正常的退款业务应当由已经生成的订单进行退款，故设立此表。

### 业务控制器WalletController
控制器端点:
- `/wallet/balance`               (查询钱包余额)
- `/wallet/consume-with-num`      (按指定金额消费，商品标记为虚拟商品)
- `/wallet/consume-with-order`    (按订单内容消费，数据通过前端统一json对象传递)
- `/wallet/refund-with-num`       (按指定金额退款，一个很别扭的方法，也存在资金安全问题)
- `/wallet/refund-with-order`     (根据已产生订单进行退款，解决上一个退款方法的资金安全问题，只能根据已产生的订单金额进行退款操作)
- `/wallet/balances`              (查询订单明细，金额流水)

业务控制层继承`BaseController`，在 该类中统一了数据的响应方法, 使用 `BaseResponse` 类统一数据回传格式.

Demo中的异常交由`ExceptionAdviceProcessor`进行统一处理,抽取出业务和接口层的异常处理逻辑代码.

> 为方便查看接口和数据模型, 项目中插入 Swagger-UI, 在 `项目URL/swagger-ui/index.html` 可进行查看.
> 
> 接口通过 PostMan工具 测试, 结果截图在 `test/image` 目录下.
> 
> SQL 文件在 `sql` 文件夹下, 文件由 Navicat 导出, 使用 sql 文件导入时需先创建数据库, 库名(`wallet_demo`), 数据库编码(`utf8mb4`),排序规则(`utf8mb4_general_ci`)