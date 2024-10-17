package lk.ijse.possystemapispring.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.possystemapispring.CustomStatusCode.SelectCustomerAndItemAndOrderErrorStatus;
import lk.ijse.possystemapispring.dao.CustomerDao;
import lk.ijse.possystemapispring.dto.CustomerStatus;
import lk.ijse.possystemapispring.dto.Impl.CustomerDTO;
import lk.ijse.possystemapispring.entity.Impl.CustomerEntity;
import lk.ijse.possystemapispring.exception.CustomerNotFoundException;
import lk.ijse.possystemapispring.exception.DataPersistException;
import lk.ijse.possystemapispring.service.CustomerService;
import lk.ijse.possystemapispring.util.AppUtil;
import lk.ijse.possystemapispring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Mapping customerMapping;
    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(AppUtil.generateCustomerId());
        CustomerEntity savedCustomer=
                customerDao.save(customerMapping.toCustomerEntity(customerDTO));
        if(savedCustomer == null){
            throw new DataPersistException("Customer not saved");
        }

    }

    @Override
    public CustomerStatus searchCustomer(String customerId) {
        if(customerDao.existsById(customerId)){
            var selectedCustomer = customerDao.getReferenceById(customerId);
            return customerMapping.toCustomerDTO(selectedCustomer);
        }else {
            return new SelectCustomerAndItemAndOrderErrorStatus(2,"Search Customer not found");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> AllCustomers=customerDao.findAll();
        return customerMapping.asCustomerDtoLIst(AllCustomers);
    }

    @Override
    public void updateCustomer(String customerId, CustomerDTO customerDTO) {
        Optional<CustomerEntity> findCustomer = customerDao.findById(customerId);
        if (!findCustomer.isPresent()){
            throw new CustomerNotFoundException("This id "+customerId+" has customer Not Found");
        }else {
            findCustomer.get().setCustomerName(customerDTO.getName());
            findCustomer.get().setCustomerAddress(customerDTO.getAddress());
            findCustomer.get().setCustomerContact(customerDTO.getContact());

        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<CustomerEntity> findCustomer = customerDao.findById(customerId);
        if (!findCustomer.isPresent()){
            throw new CustomerNotFoundException("This id "+customerId+" has customer Not Found");
        }else {
            customerDao.deleteById(customerId);
        }
    }
}
