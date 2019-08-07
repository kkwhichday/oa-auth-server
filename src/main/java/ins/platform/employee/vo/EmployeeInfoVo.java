package ins.platform.employee.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 *
 * 通过ins-framework-mybatis工具自动生成，表empemployeeinfo的VO对象<br/>
 * 对应表名：empemployeeinfo
 *
 */
@Data
public class EmployeeInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 对应字段：id */
	private Integer id;
	/** 对应字段：员工编码 */
	private String usercode;
	/** 对应字段： 员工名称*/
	private String username;
	/** 对应字段：电话 */
	private String telephone;
	/** 对应字段：学历 */
	private String education;
	/** 对应字段：email */
	private String email;
	/** 对应字段：annual_leave */
	private Integer annualLeave;
	/** 对应字段：紧急联系人 */
	private String linkman;
	/** 对应字段：紧急联系人电话 */
	private String emergencyCalls;
	/** 对应字段：性别 */
	private String sex;
	/** 对应字段：员工状态  0-离职 1-在职 2-转正 */
	private String flag;
	
	/** 对应字段：部门id */
	private Integer departmentId;
	/** 部门名称 */
	private String departmentName;
	/** 对应字段：团队id */
	private Integer teamId;
	/** 团队名称 */
	private String teamName;
	/** 对应字段：项目id */
	private Integer projectId;
	/** 项目名称 */
	private String projectName;
	
	
	/** 对应字段：岗位id */
	private String jobId;
	/** 岗位名称 */
	private String jobName;
	
	/** 对应字段：是否有电脑补助 1-是 0-否 */
	private String notebookSubsidy;
	/** 对应字段：入职日期 */
	private String hiredate;
	/** 对应字段：离职日期 */
	private String leavedate;
	/** 对应字段：是否电话补助  1-是 0-否 */
	private String cflag;
	/** 对应字段：是否驻场补助  1-是 0-否 */
	private String sflag;

	/** 新增标识  1-新增  2-修改 */
	private Integer isNew;
}
