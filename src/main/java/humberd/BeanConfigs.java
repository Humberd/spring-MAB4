package humberd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigs {

    @Bean
    public HashService getHashService() {
        return new Md5HashService();
    }
}
