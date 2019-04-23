JDBC性能优化配置：

hibernate.jdbc.batch_size
	- 意思是在批量插入、更新、删除的时候，分批与数据库服务器交互
	- 在mysql中，要使batch_size起作用，还需要配置URL连接参数：rewriteBatchedStatements=true

hibernate.jdbc.fetch_size
	- 意思是在批量查询的时候，分批从数据库中读取相应的数据到内存（而不是一次性将所有记录读取到内存）
	- 在Mysql中，本参数配置无效
	
ScollableResults
	forward_only : 光标只能向前移动
	scoll_insensitive : 可以前后移动，但对记录的改变不敏感（别人改了不知道）
	scoll_sensitive : 可以前后移动，但对记录的改变敏感（别人改了可以知道）