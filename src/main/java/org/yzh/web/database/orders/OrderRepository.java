package org.yzh.web.database.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    public List<Orders> findByCumsNumber(String number);
    public List<Orders> findByStatus(int status);
    public List<Orders> findByDriNumberAndStatus(String number,int status);
    public List<Orders> findByCumsNumberAndStatus(String number,int status);
}
