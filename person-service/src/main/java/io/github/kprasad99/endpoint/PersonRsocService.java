package io.github.kprasad99.endpoint;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.client.PersonRSocketClient;
import io.github.kprasad99.endpoint.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/rsoc/person")
public class PersonRsocService {

    private final PersonRSocketClient client;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public Flux<Person> list() {
        log.info("Listing rsocket persons");
        return client.list().map(toModel).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable("id")int id){
        log.info("Retrieving rsocket info on person with id {}", id);
        return client.findById(id).map(toModel).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<io.github.kprasad99.person.proto.PersonProto.Person, io.github.kprasad99.endpoint.model.Person> toModel = p -> mapper
            .map(p, Person.class);

}
