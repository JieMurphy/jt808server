package org.yzh.web.database.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;

    public boolean save(Goods goods)
    {
        if(goodRepository.findByGoodName(goods.getGoodName()) == null)
        {
            return false;
        }
        goodRepository.save(goods);
        return true;
    }

    public List<Goods> list()
    {
        return goodRepository.findAll();
    }
}
