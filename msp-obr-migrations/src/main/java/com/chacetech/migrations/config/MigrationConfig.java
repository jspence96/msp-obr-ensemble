package com.chacetech.migrations.config;

import com.chacetech.migrations.converter.BasicDBObjectToBigDecimalConverter;
import com.chacetech.migrations.converter.BigDecimalToBasicDBObjectConverter;
import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MigrationConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;

    @Bean
    MongoClientURI mongoClientURI() {return new MongoClientURI(mongoURI); }

    @Bean
    Mongobee mongobee(MongoTemplate mongoTemplate, Environment environment,
                      MongoClient mongoClient, MongoClientURI mongoClientURI) throws UnknownHostException {
        Mongobee runner = new Mongobee(mongoClient);
        runner.setDbName(mongoClientURI.getDatabase());
        runner.setChangeLogsScanPackage("com.chacetech");
        runner.setEnabled(true);
        runner.setSpringEnvironment(environment);
        runner.setMongoTemplate(mongoTemplate);

        return runner;
    }

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new BasicDBObjectToBigDecimalConverter());
        converters.add(new BigDecimalToBasicDBObjectConverter());
        return new CustomConversions(converters);
    }

    @Bean
    public MongoConverter mongoConverter(MongoDbFactory factory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        mappingMongoConverter.setCustomConversions(customConversions());
        mappingMongoConverter.afterPropertiesSet();
        removeConverters(mappingMongoConverter);
        return mappingMongoConverter;
    }

    private void removeConverters(MappingMongoConverter mappingMongoConverter) {
        DefaultConversionService conversionService = (DefaultConversionService)mappingMongoConverter.getConversionService();
        conversionService.removeConvertible(BigDecimal.class, String.class);
    }

}
