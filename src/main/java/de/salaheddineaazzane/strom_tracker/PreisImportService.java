package de.salaheddineaazzane.strom_tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class PreisImportService {

    private static final Logger log = LoggerFactory.getLogger(PreisImportService.class);

    private final AwattarClient client;
    private final StromPreisRepository repository;

    public PreisImportService(AwattarClient client, StromPreisRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    @Scheduled(initialDelay = 0, fixedRate = 3_600_000)
    public int importiere() {
        int neu = 0;
        try {
            for (var e : client.holePreise()) {
                neu += repository.einfuegen(
                        OffsetDateTime.ofInstant(Instant.ofEpochMilli(e.startTimestamp()), ZoneOffset.UTC),
                        OffsetDateTime.ofInstant(Instant.ofEpochMilli(e.endTimestamp()), ZoneOffset.UTC),
                        BigDecimal.valueOf(e.marketprice()).setScale(2, RoundingMode.HALF_UP));
            }
            log.info("Import fertig: {} neue Preise", neu);
        } catch (Exception ex) {
            log.error("Import fehlgeschlagen", ex);
        }
        return neu;
    }
}