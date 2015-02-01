package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.ImageEntity;
import cz.cvut.javaee2014.service.repository.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/image")
public class ImageRestController extends BaseRESTController<ImageEntity> {
 
    @Autowired
    public ImageRestController(ImageManager repo) {
        super(repo);
    }
} 