package org.jacpfx.kube.controller;

import java.util.UUID;
import org.jacpfx.kube.entity.Person;
import org.jacpfx.kube.repository.ReactiveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by amo on 04.04.17.
 */
@RestController
@RequestMapping("/api")
public class PersonController {
  @Autowired
  private ReactiveUserRepository repository;

  @GetMapping(path = "/users/{idbug}")
  public Mono<Person> get(@PathVariable("id") String uuid) {
    return repository.findById(uuid);
  }

  @GetMapping(path = "/users")
  public Flux<Person> getUsers() {
    return repository.findAll();
  }
}
