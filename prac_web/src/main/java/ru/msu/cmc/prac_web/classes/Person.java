package ru.msu.cmc.prac_web.classes;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Person implements CommonEntity, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "person_id")
    @NonNull
    private Long person_id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "last_name")
    @NonNull
    private String last_name;

    @Column(nullable = true, name = "middle_name")
    private String middle_name;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Type(type = "json")
    @Column(nullable = false, name = "address", columnDefinition = "jsonb")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone_number")
    @NonNull
    private String phone_number;

    @Type(type = "json")
    @Column(nullable = true, name = "loyalty_program_cards", columnDefinition = "jsonb")
    private String lo_cards;


    @Override
    public Integer getId() {
        return Math.toIntExact(this.person_id);
    }

    @Override
    public void setId(Integer id) {
        this.person_id = Long.valueOf(id);
    }

}
