package fun.abm.springbatchtesting.SpringBatchTesting.config;


import fun.abm.springbatchtesting.SpringBatchTesting.domain.Person;
import fun.abm.springbatchtesting.SpringBatchTesting.processor.ProcessorConfig;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class ReadPersonDetailsStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ReadersConfig readersConfig;

    @Autowired
    private ProcessorConfig processorConfig;

    @Autowired
    private DataSource dataSource;

    @Bean
     JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
    @Bean
    Step getStepForReadingPersonDetails() {
        return stepBuilderFactory.get("read-person-details")
                .<Person, Person> chunk(10)
                .reader(readersConfig.reader())
                .processor(processorConfig.processor())
                .writer(writer())
                .build();
    }

}
