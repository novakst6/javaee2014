package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.ItemEntity;
import cz.cvut.javaee2014.service.repository.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/item")
public class ItemRestController extends BaseRESTController<ItemEntity> {
 
    @Autowired
    public ItemRestController(ItemManager repo) {
        super(repo);
    }
} 