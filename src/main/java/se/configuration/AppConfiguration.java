package se.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class AppConfiguration {
	
	@Bean(destroyMethod = "shutdown")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}
		
}
