package io.github.kprasad99.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import io.github.kprasad99.person.proto.PersonProto.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonRSocketClient {

	@Autowired
	private Mono<RSocketRequester> resocketRequester;

	public Flux<Person> list() {
		return resocketRequester.flatMapMany(r -> r.route("io.github.kprasad99.person.list").retrieveFlux(Person.class));
	}

	public Mono<Person> findById(int id) {
		return resocketRequester.flatMap(r -> r.route("io.github.kprasad99.proto.person.id")
				.data(id).retrieveMono(Person.class));
	}

}
