package cz.cvut.javaee2014.controller.rest;

import cz.cvut.javaee2014.controller.rest.base.BaseRESTController;
import cz.cvut.javaee2014.model.entity.TerminalEntity;
import cz.cvut.javaee2014.service.repository.TerminalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Toms
 */
@Controller
@RequestMapping("/api/term")
public class TerminalRestController extends BaseRESTController<TerminalEntity> {
 
    @Autowired
    public TerminalRestController(TerminalManager repo) {
        super(repo);
    }
} 