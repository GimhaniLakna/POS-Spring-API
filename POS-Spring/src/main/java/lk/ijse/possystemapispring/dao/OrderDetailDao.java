package lk.ijse.possystemapispring.dao;

import lk.ijse.possystemapispring.entity.Impl.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetailEntity,String> {
}
