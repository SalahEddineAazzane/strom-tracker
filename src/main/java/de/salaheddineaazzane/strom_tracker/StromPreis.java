package de.salaheddineaazzane.strom_tracker;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "strompreise")
public class StromPreis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_zeit", nullable = false)
    private OffsetDateTime startZeit;

    @Column(name = "end_zeit", nullable = false)
    private OffsetDateTime endZeit;

    @Column(name = "preis_eur_mwh", nullable = false)
    private BigDecimal preisEurMwh;

    protected StromPreis() {
    }

    public StromPreis(OffsetDateTime startZeit, OffsetDateTime endZeit, BigDecimal preisEurMwh) {
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.preisEurMwh = preisEurMwh;
    }

    public Long getId() { return id; }
    public OffsetDateTime getStartZeit() { return startZeit; }
    public OffsetDateTime getEndZeit() { return endZeit; }
    public BigDecimal getPreisEurMwh() { return preisEurMwh; }
}