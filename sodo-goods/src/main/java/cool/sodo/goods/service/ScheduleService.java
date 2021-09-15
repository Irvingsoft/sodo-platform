package cool.sodo.goods.service;


import cool.sodo.common.starter.domain.Schedule;

import java.util.List;

public interface ScheduleService {

    void insertSchedule(Schedule schedule);

    void deleteSchedule(String scheduleId);

    void updateSchedule(Schedule schedule);

    Schedule getSchedule(String scheduleId);

    List<Schedule> listScheduleBaseByObject(String objectId);

    List<Schedule> listScheduleByObject(String objectId);

    Boolean isInSchedule(String objectId);
}
