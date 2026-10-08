package de.salaheddineaazzane.strom_tracker;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/preise")
public class StromPreisController {

    private final StromPreisRepository repository;
    private final PreisImportService importService;

    public StromPreisController(StromPreisRepository repository, PreisImportService importService) {
        this.repository = repository;
        this.importService = importService;
    }

    @GetMapping
    public List<StromPreis> alle() {
        return repository.findAll(Sort.by("startZeit"));
    }

    // Die günstigsten Stunden in den nächsten 24h: GET /preise/guenstigste?stunden=3
    @GetMapping("/guenstigste")
    public List<StromPreis> guenstigste(@RequestParam(defaultValue = "3") int stunden) {
        OffsetDateTime jetzt = OffsetDateTime.now();
        return repository.findByStartZeitBetween(jetzt, jetzt.plusHours(24),
                PageRequest.of(0, Math.min(Math.max(stunden, 1), 24), Sort.by("preisEurMwh")));
    }

    @GetMapping("/wochentag")
    public List<StromPreisRepository.WochentagDurchschnitt> proWochentag() {
        return repository.durchschnittProWochentag();
    }

    @PostMapping("/import")
    public String jetztImportieren() {
        return importService.importiere() + " neue Preise importiert";
    }
}