package springbootbatch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Lightning on 8/4/2015.
 */

@ConfigurationProperties(locations = "classpath:jdbc.properties", ignoreUnknownFields = false, prefix = "db")
@Component
@Data
public class JdbcProperties {
    String driverClassName;
    String url;
    String username;
    String password;
}
