package cool.sodo.goods.controller;

import cool.sodo.common.base.domain.Schedule;
import cool.sodo.common.base.domain.Shop;
import cool.sodo.common.base.entity.Result;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "shedule")
@Api(tags = "时间表相关接口")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @PostMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result insertSchedule(@RequestBody @Valid Schedule schedule) {

        scheduleService.insertSchedule(schedule);
        return Result.success();
    }

    @DeleteMapping(value = "/{scheduleId}")
    @ApiOperation(value = "")
    public Result deleteSchedule(@PathVariable String scheduleId) {

        scheduleService.deleteSchedule(scheduleId);
        return Result.success();
    }

    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result updateSchedule(@RequestBody Schedule schedule) {

        scheduleService.updateSchedule(schedule);
        return Result.success();
    }

    @GetMapping(value = "shop/list")
    @ApiOperation(value = "")
    public Result listScheduleByShop(@CurrentShop Shop shop) {

        return Result.success(scheduleService.listScheduleByObject(shop.getShopId()));
    }

    /*@GetMapping(value = "delivery/list")
    @ApiOperation(value = "")
    public Result listScheduleByDelivery(@CurrentUser User user) {

        // TODO 在设计好 Delivery 后将此处的 User 更换成 Delivery
        return Result.success(scheduleService.listScheduleByCurrentObject(user.getUserId()));
    }*/
}
