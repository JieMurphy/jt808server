package org.yzh.web.database.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Goods,Long> {
    public Goods findByGoodName(String name);
}
