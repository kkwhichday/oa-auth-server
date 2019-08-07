package com.oa.auth.compute.service.remote;

import feign.hystrix.FallbackFactory;
import ins.platform.leave.vo.LeaveMonthMVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 搜集请假员工
 * @author: kk
 * @create: 2019-01-18
 **/
@FeignClient(value = "leave-server", fallbackFactory = FeignClientFallbackFactory.class)
public interface TopLeaver {
    /*
     * @Author kk
     * @Description 迟到top10
     * @Date 2019/1/18
     * @Param []
     * @return ins.platform.attendance.vo.ExpUserDataVo
     **/
    @GetMapping(value="/leave-api/basic/selectCompletionAndLeave/{yearMonth}")
    List<LeaveMonthMVo> getLeaveTop10(@PathVariable(value="yearMonth") String yearMonth);


}
@Component
class FeignClientTopLeaverFallback implements TopLeaver {

    @Override
    public List<LeaveMonthMVo> getLeaveTop10(String yearMonth) {
        List<LeaveMonthMVo> list = new ArrayList<>();
        return list;
    }
}
@Component
class FeignClientFallbackFactory implements FallbackFactory<TopLeaver> {
    private static final Logger logger = LoggerFactory.getLogger(FeignClientFallbackFactory.class);
    @Override
    public TopLeaver create(Throwable cause) {
        return new TopLeaver() {
            @Override
            public List<LeaveMonthMVo> getLeaveTop10(String yearMonth) {
                logger.error("fallback; reason was:", cause);
                List<LeaveMonthMVo> list = new ArrayList<>();
                return list;
            }
        };
    }
}
