package lk.ijse.possystemapispring.dto.Impl;

import lk.ijse.possystemapispring.dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO implements CustomerStatus {
    private String id;
    private String name;
    private String address;
    private String contact;
}
