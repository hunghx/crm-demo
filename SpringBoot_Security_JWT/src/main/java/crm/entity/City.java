package crm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    private Long id;
    @Column(name = "city")
    private String name;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
