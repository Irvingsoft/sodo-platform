package cool.sodo.log.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.log.entity.LogApiDTO;
import cool.sodo.log.service.LogApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "api")
public class LogApiController {

    @Resource
    private LogApiService logApiService;

    @GetMapping(value = "{id}")
    public Result getLogApiInfoDetail(@PathVariable String id) {

        return Result.success(logApiService.getLogApiInfoDetail(id));
    }

    @PostMapping(value = "page")
    public Result pageLogApiBaseDetail(@RequestBody LogApiDTO logApiDTO) {

        return Result.success(logApiService.pageLogApiBaseDetail(logApiDTO));
    }
}
