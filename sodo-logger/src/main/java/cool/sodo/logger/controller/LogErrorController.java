package cool.sodo.logger.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.logger.entity.LogErrorRequest;
import cool.sodo.logger.service.LogErrorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "error")
public class LogErrorController {

    @Resource
    private LogErrorService logErrorService;

    @GetMapping(value = "{id}")
    public Result getLogErrorInfo(@PathVariable String id) {

        return Result.success(logErrorService.getLogErrorInfo(id));
    }

    @PostMapping(value = "page")
    public Result pageLogErrorBase(@RequestBody LogErrorRequest logErrorRequest) {

        return Result.success(logErrorService.pageLogErrorBase(logErrorRequest));
    }
}
