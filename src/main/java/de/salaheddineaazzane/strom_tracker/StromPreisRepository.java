package de.salaheddineaazzane.strom_tracker;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public interface StromPreisRepository extends JpaRepository<StromPreis, Long> {

    List<StromPreis> findByStartZeitBetween(OffsetDateTime von, OffsetDateTime bis, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO strompreise (start_zeit, end_zeit, preis_eur_mwh)
            VALUES (:s, :e, :p) ON CONFLICT (start_zeit) DO NOTHING
            """, nativeQuery = true)
    int einfuegen(@Param("s") OffsetDateTime s, @Param("e") OffsetDateTime e, @Param("p") BigDecimal p);

    @Query(value = """
            SELECT EXTRACT(ISODOW FROM start_zeit AT TIME ZONE 'Europe/Berlin')::int AS wochentag,
                   AVG(preis_eur_mwh)::float8 AS durchschnitt
            FROM strompreise
            GROUP BY 1 ORDER BY 1
            """, nativeQuery = true)
    List<WochentagDurchschnitt> durchschnittProWochentag();

    interface WochentagDurchschnitt {
        Integer getWochentag();   // 1 = Montag ... 7 = Sonntag
        Double getDurchschnitt();
    }
}