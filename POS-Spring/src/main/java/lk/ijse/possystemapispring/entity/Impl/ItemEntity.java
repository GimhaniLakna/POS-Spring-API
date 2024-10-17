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
@Table(name = "item")
public class ItemEntity implements SuperEntity {
    @Id
    private String ItemId;
    private String ItemName;
    private int qty;
    private double unitPrice;
    @OneToMany(mappedBy = "item")
    private List<OrderDetailEntity> orders;
}
