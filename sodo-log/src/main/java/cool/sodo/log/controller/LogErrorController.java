package cool.sodo.log.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.log.entity.LogErrorDTO;
import cool.sodo.log.service.LogErrorService;
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
    public Result pageLogErrorBase(@RequestBody LogErrorDTO logErrorDTO) {

        return Result.success(logErrorService.pageLogErrorBase(logErrorDTO));
    }
}
