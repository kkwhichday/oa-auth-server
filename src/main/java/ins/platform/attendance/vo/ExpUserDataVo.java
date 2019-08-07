package ins.platform.attendance.vo;

import ins.platform.leave.vo.LeaveMonthMVo;
import lombok.Data;

import java.util.List;

/**
 * @description: 违纪员工排名
 * @author: kk
 * @create: 2019-01-18
 **/
@Data
public class ExpUserDataVo {
    private static final long serialVersionUID = 1L;
    private String usercode;
    private String username;
    private Integer count;


}
