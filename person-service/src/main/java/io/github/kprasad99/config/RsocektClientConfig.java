package io.github.kprasad99.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(PersonServiceProperties.class)
public class RsocektClientConfig {

    @Bean
    Mono<RSocketRequester> rSocketRequester(RSocketRequester.Builder rsocketRequesterBuilder,
            RSocketStrategies strategies, PersonServiceProperties props) {

        return Mono.defer(() -> rsocketRequesterBuilder.rsocketStrategies(strategies)
                .dataMimeType(new MimeType("application", "x-protobuf"))
                .connectTcp(props.getHost(), props.getPorts().getRsocket()));

    }

    @Bean
    public RSocketStrategiesCustomizer protobufRSocketStrategyCustomizer() {
        return (strategy) -> {
            strategy.decoder(new ProtobufDecoder());
            strategy.encoder(new ProtobufEncoder());
        };
    }

}
