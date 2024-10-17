package lk.ijse.possystemapispring.dto.Impl;

import lk.ijse.possystemapispring.dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO implements ItemStatus {
    private String id;
    private String name;
    private int qty;
    private double unitPrice;
}
