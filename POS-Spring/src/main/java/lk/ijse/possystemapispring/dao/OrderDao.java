package lk.ijse.possystemapispring.dao;

import lk.ijse.possystemapispring.entity.Impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity,String> {

}
