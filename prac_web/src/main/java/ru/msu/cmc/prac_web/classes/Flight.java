package ru.msu.cmc.prac_web.classes;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity
@Table(name = "flight")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Flight implements CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "flight_id")
    @NonNull
    private Integer flight_id;

    @Column(nullable = false, name = "flight_no")
    @NonNull
    private String flight_no;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_airport_id")
    @ToString.Exclude
    @NonNull
    private Airport dep_airport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_airport_id")
    @ToString.Exclude
    @NonNull
    private Airport arr_airport;

    @Column(nullable = false, name = "departure_date_time")
    @NonNull
    private Timestamp dep_time;

    @Column(nullable = false, name = "arrival_date_time")
    @NonNull
    private Timestamp arr_time;

    @Type(type = "json")
    @Column(nullable = false, name = "free_seats", columnDefinition = "jsonb")
    @NonNull
    private String free_seats;

    @Column(nullable = false, name = "airline")
    @NonNull
    private String airline;

    @Override
    public Integer getId() {
        return flight_id;
    }

    @Override
    public void setId(Integer id) {
        this.flight_id = id;
    }
}
