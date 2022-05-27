package ru.msu.cmc.prac_web.classes;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Ticket implements CommonEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "ticket_id")
    @NonNull
    private Long ticket_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    @ToString.Exclude
    @NonNull
    private Flight flight_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @NonNull
    private Person person_id;

    @Column(nullable = false, name = "price")
    @NonNull
    private Long price;

    @Column(nullable = false, name = "seat_class")
    @NonNull
    private String seat_class;

    @Override
    public Integer getId() {
        return Math.toIntExact(this.ticket_id);
    }

    @Override
    public void setId(Integer id) {
        this.ticket_id = Long.valueOf(id);
    }
}
