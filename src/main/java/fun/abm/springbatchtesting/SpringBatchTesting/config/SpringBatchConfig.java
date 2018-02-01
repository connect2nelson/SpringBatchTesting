package fun.abm.springbatchtesting.SpringBatchTesting.config;

import fun.abm.springbatchtesting.SpringBatchTesting.listeners.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private ReadPersonDetailsStepConfig stepConfig;

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepConfig.getStepForReadingPersonDetails())
                .end()
                .build();
    }


}
