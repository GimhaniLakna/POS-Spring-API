package lk.ijse.possystemapispring.service.Impl;

import lk.ijse.possystemapispring.dao.CustomerDao;
import lk.ijse.possystemapispring.dao.ItemDao;
import lk.ijse.possystemapispring.dao.OrderDao;
import lk.ijse.possystemapispring.dao.OrderDetailDao;
import lk.ijse.possystemapispring.dto.Impl.OrderDTO;
import lk.ijse.possystemapispring.dto.Impl.OrderDetailsDTO;
import lk.ijse.possystemapispring.entity.Impl.CustomerEntity;
import lk.ijse.possystemapispring.entity.Impl.ItemEntity;
import lk.ijse.possystemapispring.entity.Impl.OrderDetailEntity;
import lk.ijse.possystemapispring.entity.Impl.OrderEntity;
import lk.ijse.possystemapispring.exception.DataPersistException;
import lk.ijse.possystemapispring.exception.ItemNotFoundException;
import lk.ijse.possystemapispring.service.OrderService;
import lk.ijse.possystemapispring.util.AppUtil;
import lk.ijse.possystemapispring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private Mapping orderMapping;

    @Override
    public void placeOrder(OrderDTO orderDTO, List<OrderDetailsDTO> orderDetailsDTOList) {
        orderDTO.setOrderId(AppUtil.generateOrderId());
        Optional<CustomerEntity>customer = customerDao.findById(orderDTO.getCustomerId());
        OrderEntity tempOrder = orderMapping.toOrderEntity(orderDTO);
        tempOrder.setCustomer(customer.get());

        OrderEntity savedOrder = orderDao.save(tempOrder);
        if (savedOrder==null){
            throw new DataPersistException("Order is not saved !");
        }
        //order Details and Item

        for(OrderDetailsDTO orderDetailsDTO : orderDetailsDTOList){
            OrderDetailEntity orderDetailEntity = orderMapping.toOrderDetailEntity(orderDetailsDTO);
            orderDetailEntity.setOrder(savedOrder);

            Optional<ItemEntity>selectItem= itemDao.findById(orderDetailsDTO.getItemId());
            if (selectItem==null){
                throw new ItemNotFoundException("Item is not found!");
            }
            orderDetailEntity.setItem(selectItem.get());
            OrderDetailEntity savedOrderDetail=orderDetailDao.save(orderDetailEntity);

            //Item
            ItemEntity item=selectItem.get();
            int updateQty= item.getQty() - orderDetailsDTO.getQty();

            item.setQty(updateQty);
            ItemEntity updateItem=itemDao.save(item);

            if (savedOrderDetail == null || updateItem == null) {
                throw new DataPersistException("Order Detail not saved");
            }

        }

    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> allOrders=orderDao.findAll();
        return orderMapping.asOrderDtoLIst(allOrders);
    }
}
