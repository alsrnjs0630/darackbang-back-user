package com.lab.darackbang.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // 필드 매칭 활성화. 객체의 필드가 동일한 이름 가진 경우 자동 매핑
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                // 프라이빗 필드에 대한 접근 허용
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // 매칭 전략 : 느슨한 방식 -> 소스와 객체 필드 이름이 정확히 일치하지 않아도 매핑 가능
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;
    }
}
