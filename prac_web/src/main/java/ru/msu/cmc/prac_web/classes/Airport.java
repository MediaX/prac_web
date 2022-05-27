package ru.msu.cmc.prac_web.classes;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "airport")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Airport implements CommonEntity{
    @Id
    @Column(nullable = false, name = "airport_id")
    @NonNull
    private Integer id;

    @Column(nullable = false, name = "airport_code")
    @NonNull
    private String code;

    @Column(nullable = false, name = "airport_name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "city")
    @NonNull
    private String city;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
