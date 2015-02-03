package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.ItemOrderEntity;
import cz.cvut.javaee2014.service.repository.ItemOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/item-order")
public class ItemOrderRestController extends BaseRESTController<ItemOrderEntity> {
 
    @Autowired
    public ItemOrderRestController(ItemOrderManager repo) {
        super(repo);
    }
} 