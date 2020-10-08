package de.betoffice.openligadb.spring;

import java.util.ArrayList;
import java.util.List;

import de.betoffice.openligadb.json.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IoApplication {

    private static final Logger log = LoggerFactory.getLogger(IoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
        );

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new Interceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            Match[] matches = restTemplate.getForObject(
                    "https://www.openligadb.de/api/getmatchdata/bl1/2020/1", Match[].class);

            for (Match match : matches) {
                log.info(match.toString());
            }
        };
    }

}
