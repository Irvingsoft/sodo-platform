package cool.sodo.log.controller;

import cool.sodo.common.base.entity.Result;
import cool.sodo.log.service.LogBusinessService;
import cool.sodo.log.starter.entity.LogBusinessDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "business")
public class LogBusinessController {

    @Resource
    private LogBusinessService logBusinessService;

    @GetMapping(value = "{id}")
    public Result getLogBusiness(@PathVariable String id) {

        return Result.success(logBusinessService.getBusinessInfo(id));
    }

    @PostMapping(value = "page")
    public Result pageLogBusiness(@RequestBody LogBusinessDTO logBusinessDTO) {

        return Result.success(logBusinessService.pageLogBusinessBase(logBusinessDTO));
    }

}
