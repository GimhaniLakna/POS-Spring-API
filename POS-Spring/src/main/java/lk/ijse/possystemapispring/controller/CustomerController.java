package lk.ijse.possystemapispring.controller;

import lk.ijse.possystemapispring.CustomStatusCode.SelectCustomerAndItemAndOrderErrorStatus;
import lk.ijse.possystemapispring.dto.CustomerStatus;
import lk.ijse.possystemapispring.dto.Impl.CustomerDTO;
import lk.ijse.possystemapispring.exception.CustomerNotFoundException;
import lk.ijse.possystemapispring.exception.DataPersistException;
import lk.ijse.possystemapispring.service.CustomerService;
import lk.ijse.possystemapispring.util.RegexProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private static Logger logger= LoggerFactory.getLogger(CustomerController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            customerService.saveCustomer(customerDTO);
            logger.info("Customer saved successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.error("Customer saved fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Customer saved fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{customerID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus searchCustomer(@PathVariable ("customerID") String customerId){
        if (!RegexProcess.customerIdMatcher(customerId)) {
            logger.error("Customer search fail!");
            return new SelectCustomerAndItemAndOrderErrorStatus(1,"Customer ID is not valid");
        }
        return customerService.searchCustomer(customerId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @PutMapping(value = "/{customerID}")
    public ResponseEntity<Void> updateCustomer(@PathVariable ("customerID") String customerId,
                                           @RequestBody CustomerDTO updateCustomerDTO){
        try {
            if (!RegexProcess.customerIdMatcher(customerId) || updateCustomerDTO == null){

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.updateCustomer(customerId,updateCustomerDTO);
            logger.info("Customer updated successfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            logger.error("Customer not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("Customer updated fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping(value = "/{customerID}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable ("customerID") String customerId){
        try {
            if (!RegexProcess.customerIdMatcher(customerId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteCustomer(customerId);
            logger.info("Customer Deleted successfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            logger.error("Customer not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("Customer deleted fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
