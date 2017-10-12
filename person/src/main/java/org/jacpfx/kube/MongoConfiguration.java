package org.jacpfx.kube;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Created by amo on 04.04.17.
 */

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

  private MongoProperties mongoProperties;



  private Environment environment;

  @Bean
  public LoggingEventListener mongoEventListener() {
    return new LoggingEventListener();
  }

  @Value("${mongo.database}")
  private String database;

  @Value("${mongo.database.url}")
  private String url;


  @Autowired
  public MongoConfiguration(MongoProperties mongoProperties) {
    this.mongoProperties = mongoProperties;
  }
  @Autowired
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  @Bean
  ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder.json().build();
  }

  @Override
  protected String getDatabaseName() {
    return mongoProperties.getDatabase();
  }


  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create(url);
  }
}