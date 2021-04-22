package com.paulocorrea.desafiopagseguro.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class YahooFinancesRepository {

    public static final String AUTOCOMPLETE_URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/auto-complete";

    public static final String GET_QUOTES_URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes";

    public static final String REGION = "BR";

    public String obterValorDaAcao(String simbolo) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GET_QUOTES_URL)
                .queryParam("region", REGION)
                .queryParam("symbols", simbolo);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode quoteResponse = root.path("quoteResponse");
        JsonNode result = quoteResponse.path("result").path(0);
        JsonNode regularMarketPrice = result.path("regularMarketPrice");
        return regularMarketPrice.asText();
    }

    public String obterSimboloDaAcao(String codigo) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTOCOMPLETE_URL)
                .queryParam("region", REGION)
                .queryParam("q", codigo);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode quotes = root.path("quotes");
        JsonNode symbol = quotes.path(0).path("symbol");

        return symbol.asText();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Rapidapi-Key", "e5bbbd75f2mshf8680b2795f2fc5p1d260ajsnb1000ffd215a");
        headers.set("X-Rapidapi-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
        return headers;
    }
}
