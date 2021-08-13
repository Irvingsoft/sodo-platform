package cool.sodo.housekeeper.controller;

import cool.sodo.housekeeper.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Resource
    private MenuService menuService;


}
