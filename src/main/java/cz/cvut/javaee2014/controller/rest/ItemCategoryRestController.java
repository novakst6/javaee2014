package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.ItemCategoryEntity;
import cz.cvut.javaee2014.service.repository.ItemCategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/item-cat")
public class ItemCategoryRestController extends BaseRESTController<ItemCategoryEntity> {
 
    @Autowired
    public ItemCategoryRestController(ItemCategoryManager repo) {
        super(repo);
    }
} 