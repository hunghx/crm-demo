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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;
    @Column(name="address")
    private String detail;
    private  String district;
    @Column(name = "postal_code")
    private String code;
    private String phone;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
