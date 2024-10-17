package lk.ijse.possystemapispring.dto.Impl;

import lk.ijse.possystemapispring.dto.OrderDetailsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailsDTO implements OrderDetailsStatus {
    /*private String orderId;*/
    private String itemId;
    private int qty;
    private double unitPrice;
    private double total;
}
