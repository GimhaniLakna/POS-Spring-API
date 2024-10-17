package lk.ijse.possystemapispring.entity.Impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lk.ijse.possystemapispring.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity implements SuperEntity {
    @Id
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerContact;
    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
}
