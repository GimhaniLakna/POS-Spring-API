package lk.ijse.possystemapispring.entity.Impl;

import jakarta.persistence.*;
import lk.ijse.possystemapispring.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements SuperEntity {
    @Id
    private String orderId;
    @ManyToOne
    @JoinColumn(name = "CustomerId",referencedColumnName = "customerId")
    private CustomerEntity customer;
    private String date;
    private double netTotal;
    private double discount;
    private double subTotal;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetails;
}
