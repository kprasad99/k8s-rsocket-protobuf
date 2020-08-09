package io.github.kprasad99;

import org.modelmapper.ModelMapper;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;


@Configuration
public class AppConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

//    @Bean
//    public RSocketServerCustomizer rsocketCustomizer() {
//        return (rsoc) -> {
//            rsoc.resume(new Resume());
//        };
//    }

    @Bean
    public RSocketStrategiesCustomizer protoRSocketStrategyCustomizer() {
        return (strategy) -> {
            strategy.decoder(new ProtobufDecoder());
            strategy.encoder(new ProtobufEncoder());
        };
    }
}
