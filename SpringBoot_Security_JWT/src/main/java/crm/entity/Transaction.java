package crm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "total_amount")
    private int totalAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "planned_payment_date")
    private LocalDate plannedPaymentDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "actual_payment_date")
    private LocalDate actualPaymentDate;
    @Column(name = "payment_method")
    private String paymentMethod;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
}
