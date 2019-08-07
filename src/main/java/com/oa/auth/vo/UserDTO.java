
package com.oa.auth.vo;

import com.oa.auth.model.User;
import lombok.Data;
import java.util.List;

/**
 * @author kk
 *
 * @date 2018/11/5
 */
@Data
public class UserDTO extends User {
    /**
     * 角色ID
     */
    private List<Integer> role;

//    private Integer deptId;

    /**
     * 新密码
     */
    private String newpassword1;
}
