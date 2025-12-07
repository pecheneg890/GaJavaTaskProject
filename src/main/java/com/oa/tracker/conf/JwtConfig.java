package com.oa.tracker.conf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("jwt")
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {
    private String key;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

}
