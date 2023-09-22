package hello.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Slf4j
//@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource(){
        log.info("dataSource 빈 등록");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:test"); // JVM 안에서 memory 모드로 사용 테스트 할 때 편함
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager(){
        log.info("transactionManager 빈 등록");
        return new JdbcTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        log.info("jdbcTemplate 빈 등록");
        return new JdbcTemplate(dataSource());
    }
}
