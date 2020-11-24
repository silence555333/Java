package kw.kd.dss;


import kw.kd.dss.aop.DynamicDataSourceAnnotationAdvisor;
import kw.kd.dss.aop.DynamicDataSourceAnnotationInterceptor;
import kw.kd.dss.register.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(DynamicDataSourceRegister.class)
@MapperScan("kw.kd.dss.repository")
@SpringBootApplication
@EnableTransactionManagement
public class Chapter5Application {
    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }
    public static void main(String[] args) {
        SpringApplication.run(Chapter5Application.class, args);
    }
}
