package de.salaheddineaazzane.strom_tracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public class AwattarClient {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Antwort(List<Eintrag> data) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Eintrag(
            @JsonProperty("start_timestamp") long startTimestamp,
            @JsonProperty("end_timestamp") long endTimestamp,
            @JsonProperty("marketprice") double marketprice) {}

    private final RestClient restClient = RestClient.create("https://api.awattar.de");

    public List<Eintrag> holePreise() {
        Antwort antwort = restClient.get().uri("/v1/marketdata").retrieve().body(Antwort.class);
        return antwort == null || antwort.data() == null ? List.of() : antwort.data();
    }
}