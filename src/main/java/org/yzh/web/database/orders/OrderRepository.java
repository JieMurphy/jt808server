package org.yzh.web.database.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    public List<Orders> findByCumsNumber(long number);
    public List<Orders> findByStatus(int status);
    public List<Orders> findByDriNumberAndStatus(long number,int status);
    public List<Orders> findByCumsNumberAndStatus(long number,int status);
}
