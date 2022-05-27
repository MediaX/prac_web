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
@Table(name = "transaction_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Transaction_history implements CommonEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "transaction_id")
    @NonNull
    private Long transaction_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @NonNull
    private Person person_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    @ToString.Exclude
    @NonNull
    private Flight flight_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    @ToString.Exclude
    @NonNull
    private Ticket ticket_id;

    @Column(nullable = false, name = "transaction_date_time")
    @NonNull
    private Timestamp transaction_day_time;

    @Type(type = "json")
    @Column(nullable = true, name = "used_loyalty_card", columnDefinition = "jsonb")
    private String used_lo_card;

    @Column(nullable = false, name = "total_sum_payed")
    @NonNull
    private Long sum;

    @Column(nullable = false, name = "discount_loyalty_card")
    @NonNull
    private Long discount;

    @Override
    public Integer getId() {
        return Math.toIntExact(this.transaction_id);
    }

    @Override
    public void setId(Integer id) {
        this.transaction_id = Long.valueOf(id);
    }
}
