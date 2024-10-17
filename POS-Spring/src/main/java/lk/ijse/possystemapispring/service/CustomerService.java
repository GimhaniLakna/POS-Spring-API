package lk.ijse.possystemapispring.service;

import lk.ijse.possystemapispring.dto.CustomerStatus;
import lk.ijse.possystemapispring.dto.Impl.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);

    CustomerStatus searchCustomer(String customerId);

    List<CustomerDTO> getAllCustomers();

    void updateCustomer(String customerId, CustomerDTO customerDTO);

    void deleteCustomer(String customerId);
}
