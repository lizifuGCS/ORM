package com.hand.app.myorm.query;

import com.hand.app.myorm.core.Query;
import com.hand.app.myorm.domain.My_orm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 13:25
 * @Version 1.0
 */
@RestController
public class OperationController   {



 /*   @GetMapping("/get/orm/data")
    public Object  getData(){

    }*/

    @PostMapping("/get/orm/data")
    public void   saveData(@RequestBody My_orm o){
        Query query = new Query();
        query.save(o);
    }


}
