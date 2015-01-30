package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.TerminalTypeEntity;
import cz.cvut.javaee2014.service.repository.TerminalTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/term-type")
public class TerminalTypeRestController extends BaseRESTController<TerminalTypeEntity> {
 
    @Autowired
    public TerminalTypeRestController(TerminalTypeManager repo) {
        super(repo);
    }
} 