package be.about.coding.codequality.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"database.properties"})
public class Config {

}
