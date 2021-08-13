package cool.sodo.housekeeper.service.impl;

import cool.sodo.housekeeper.mapper.MenuMapper;
import cool.sodo.housekeeper.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
}
