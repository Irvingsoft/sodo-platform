package cool.sodo.logger.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.logger.entity.LogApiRequest;
import cool.sodo.logger.service.LogApiService;
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
    public Result pageLogApiBaseDetail(@RequestBody LogApiRequest logApiRequest) {

        return Result.success(logApiService.pageLogApiBaseDetail(logApiRequest));
    }

}
