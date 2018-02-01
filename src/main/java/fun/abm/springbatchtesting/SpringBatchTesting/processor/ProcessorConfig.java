package fun.abm.springbatchtesting.SpringBatchTesting.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProcessorConfig {

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

}
