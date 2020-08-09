package io.github.kprasad99.endpoint;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import io.github.kprasad99.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PersonRsocService {

    private final PersonDao personDao;
    @Autowired
    private ModelMapper mapper;

    @MessageMapping("io.github.kprasad99.person.list")
    public Flux<Person> list(){
        log.info("Listing persons");
        return Flux.fromIterable(personDao.findAll()).map(toProto).map(Person.Builder::build);
    }

    @MessageMapping("io.github.kprasad99.person.id")
    public Mono<Person> get(int id){
        log.info("Retrieving info on person with id {}", id);
        return Mono.fromSupplier(()->personDao.findById(id).orElse(null)).map(toProto).map(Person.Builder::build);
    }

    private Function<io.github.kprasad99.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);

}
