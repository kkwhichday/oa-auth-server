package com.oa.auth.compute.service.remote;

import feign.hystrix.FallbackFactory;
import ins.platform.attendance.vo.ExpUserDataVo;
import ins.platform.leave.vo.LeaveMonthMVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 搜集违纪员工
 * @author: kk
 * @create: 2019-01-18
 **/
@FeignClient(value = "emp-server",fallbackFactory = FeignClientEmpFallbackFactory.class)
public interface TopBreaker {
    /*
     * @Author kk
     * @Description 迟到top10
     * @Date 2019/1/18
     * @Param []
     * @return ins.platform.attendance.vo.ExpUserDataVo
     **/
//    @RequestMapping(value="/attendance/getEmpLateTop10", method=RequestMethod.GET)
//    List<ExpUserDataVo> getEmpLateTop10(@RequestParam("yearMonth") final String yearMonth);
//
//    /*
//     * @Author kk
//     * @Description 早退top10
//     * @Date 2019/1/18
//     * @Param []
//     * @return ins.platform.attendance.vo.ExpUserDataVo
//     **/
//    @RequestMapping(value="/attendance/getEmpEarlyTop10", method= RequestMethod.GET)
//    List<ExpUserDataVo> getEmpEarlyTop10(@RequestParam("yearMonth") final String yearMonth);

    @RequestMapping(value = "/attendance/getEmpLateAndEarlyTop10", method = RequestMethod.GET)
    List<ExpUserDataVo> getEmpLateAndEarlyTop10(@RequestParam("yearMonth") final String yearMonth);



}
@Component
class FeignClientTopBreakerFallback implements TopBreaker {
    @Override
    public List<ExpUserDataVo> getEmpLateAndEarlyTop10(String yearMonth) {
        List<ExpUserDataVo> list = new ArrayList<>();
        return list;
    }
}

@Component
class FeignClientEmpFallbackFactory implements FallbackFactory<TopBreaker> {
    private static final Logger logger = LoggerFactory.getLogger(FeignClientEmpFallbackFactory.class);
    @Override
    public TopBreaker create(Throwable cause) {
        return new TopBreaker() {
            @Override
            public List<ExpUserDataVo> getEmpLateAndEarlyTop10(String yearMonth) {
                logger.error("getEmpLateAndEarlyTop10-fallback; reason was:", cause);
                List<ExpUserDataVo> list = new ArrayList<>();
                return list;
            }
        };
    }
}


