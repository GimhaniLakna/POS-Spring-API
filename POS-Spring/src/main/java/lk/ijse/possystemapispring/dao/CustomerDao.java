package lk.ijse.possystemapispring.dao;

import lk.ijse.possystemapispring.entity.Impl.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<CustomerEntity,String> {
}
