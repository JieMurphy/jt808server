package org.yzh.web.database.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<Goods,Long> {
    public Goods findByGoodName(String name);
    public List<Goods> findByKind(int kind);
}
