# yml添加配置
	spring.profiles.active = profileActive
# pom添加配置
	<profiles>
		<profile>
			<id>dev</id>
			<properties>
				// 注意  这儿就对应的是 yml 中的 spring.profiles.active = profileActive(这个可以自己定义的一个变量 只要对应就行)
				<profileActive>dev</profileActive>
			</properties>
			// 环境控制 true 表示采用的当前环境
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
		</profile>
		<profile>
			<id>test</id>
			<properties>
				<profileActive>test</profileActive>
			</properties>
		</profile>
	</profiles>