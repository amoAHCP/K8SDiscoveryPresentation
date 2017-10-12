package org.jacpfx.kube;

import org.jacpfx.kube.entity.Person;
import org.jacpfx.kube.repository.ReactiveUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableConfigurationProperties({MongoProperties.class})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class PersonApplication {


  public static void main(String[] args) {
    SpringApplication.run(PersonApplication.class, args);
  }

  @Bean
  WebClient webclient() {
    return WebClient.create();
  }

  @Bean
  CommandLineRunner initData(ReactiveUserRepository personRepository) {
    Flux<Person> people = Flux.just(
        new Person("1", "Eric", "Foo"),
        new Person("2", "Raymond", "Bar"),
        new Person("3", "Paul", "Baz")
    );

    return args -> {
      personRepository.deleteAll().thenMany(personRepository.saveAll(people)).blockLast();
    };

  }
}
