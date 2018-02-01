package fun.abm.springbatchtesting.SpringBatchTesting.config;

import fun.abm.springbatchtesting.SpringBatchTesting.SpringBatchTestingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        JobLauncherTestUtils.class,
        SpringBatchTestingApplication.class,
        SpringBatchConfig.class})

public class SpringBatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testJob() throws Exception {
        jdbcTemplate.update("delete from people");
        for (int i = 1; i <= 10; i++) {
            jdbcTemplate.update("insert into people (first_name, last_name) values ( ?, ?)",
                    "Nelson", "Davenapalli");
        }

        BatchStatus jobExecution = jobLauncherTestUtils.launchJob().getStatus();

        assertThat("COMPLETED").isEqualTo(jobExecution.getBatchStatus().toString());
    }

}