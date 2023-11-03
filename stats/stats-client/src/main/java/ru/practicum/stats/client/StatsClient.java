package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.stats.dto.StatsHitCreate;
import ru.practicum.stats.dto.StatsHitResponse;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.practicum.utils.Patterns.*;

@Component
public class StatsClient {
    private final WebClient webClient;

    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }

    public void postHit(StatsHitCreate dto) {
        webClient.post()
                .uri(HIT_PREFIX)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(StatsHitCreate.class)
                .block();
    }

    public List<StatsHitResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(STATS_PREFIX)
                        .queryParam("start", start.format(ofPattern(DATE_TIME)))
                        .queryParam("end", end.format(ofPattern(DATE_TIME)))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(StatsHitResponse.class)
                .collectList()
                .block();
    }
}