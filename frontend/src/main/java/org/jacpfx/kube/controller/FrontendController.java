package org.jacpfx.kube.controller;

import io.fabric8.annotations.ServiceName;

import io.fabric8.annotations.WithLabel;
import io.fabric8.annotations.WithLabels;
import org.jacpfx.kube.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class FrontendController {


  @ServiceName
  @WithLabels(value = {@WithLabel(name = "version", value = "${label.byId.version}"),@WithLabel(name = "name", value = "${label.byId.name}")})
  private String byId;

  @ServiceName
  @WithLabels(value = {@WithLabel(name = "version", value = "${label.all.version}"),@WithLabel(name = "name", value = "${label.all.name}")})
  private String all;

  @Autowired
  private WebClient client;


  @GetMapping(path = "/users/{id}")
  public Mono<Person> get(@PathVariable("id") String uuid) {
    return this.client
        .get().uri("http://" + getById() + "/api/users/{id}", uuid)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(resp -> resp.bodyToMono(Person.class));
  }

  @GetMapping(path = "/users")
  public Flux<Person> getUsers() {
    return this.client
        .get().uri("http://" + getAll() + "/api/users")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMapMany(response -> response.bodyToFlux(Person.class));
  }



  private String getById() {
    System.out.println("HOST: " + byId);
    return byId != null ? byId : readhost;
  }

  private String getAll() {
    System.out.println("HOST-ALL: " + all);
    return all != null ? all : readhost;
  }

  @Value("${person.host}")
  public String readhost;
}
