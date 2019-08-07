package com.oa.auth.compute.handler;


import com.oa.auth.compute.constants.EmpConstants;
import com.oa.auth.compute.vo.Bug;
import com.oa.auth.compute.vo.Employee;
import com.oa.auth.compute.vo.Requirement;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 处理excel导入的员工信息
 * @author: kk
 * @create: 2018-12-21
 **/
@Component("employeeExcelHandler")
public class EmployeeExcelHandler implements ExcelHandler<List<Employee>>{
    private String deployVersion=null;

    @Override
    public List<Employee> handle(Workbook workbook) {

        List<Employee> list = new ArrayList<Employee>();
        //得到一个工作表
        Sheet sheet =workbook.getSheet("开发人员需求缺陷统计");

        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        Employee temp=null;
        Employee leader=null;
        int groupNo=1;

        deployVersion = sheet.getRow(3).getCell(0).getStringCellValue();
        //获得所有数据
        for (int i = 3; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);


            //获得获得第i行第2列的 String类型对象
            Cell cell = row.getCell((short) 1);

            String cellValue = cell.getStringCellValue();
            if(!cellValue.isEmpty()){
                if("小计".equals(cellValue)) {
                    double r = row.getCell((short) 7).getNumericCellValue();
                    double b = row.getCell((short) 12).getNumericCellValue();
                    EmpConstants.rbv = new BigDecimal(r/b).setScale(2, BigDecimal.ROUND_HALF_UP);;
                    break;
                }
                temp= new Employee();
                temp.setName(cellValue.substring(cellValue.indexOf("（")+1,cellValue.indexOf("）")));
                temp.setGroupNo(groupNo++);
                temp.setELevel(EmpConstants.GroupLeader);


                if(leader!=null){
                    leader.getChildren().add(temp);
                }else{
                    list.add(temp);
                }
            }else if(row.getCell(3).getStringCellValue().trim().equals("版本管理")){
                //为空，说明这一行是项目经理的数据
                leader= new Employee();
                leader.setName(row.getCell(2).getStringCellValue());
                leader.setGroupNo(null);
                leader.setELevel(EmpConstants.ProjectLeader);

                list.add(leader);
            }
            Cell scell = row.getCell((short) 2);


            String scellValue = scell.getStringCellValue();
            double rcount1 = row.getCell((short) 4).getNumericCellValue();
            double rcount2 = row.getCell((short) 5).getNumericCellValue();
            List<Requirement> requirements = new ArrayList<Requirement>();
            Requirement r = new Requirement();
            r.setRCount((int)(rcount1+rcount2));
            r.setRLevel(-1);//暂时没有级别
            r.setDeployVersion(deployVersion);
            requirements.add(r);

            /*for(int level=1;level<=4;level++){
                Requirement r = new Requirement();
                r.setRCount((int)rcount/4);
                r.setRLevel(level);
                r.setRType(0);
                requirements.add(r);
            }*/

            List<Bug> bugs = new ArrayList<Bug>();
            double bcount = row.getCell(12).getNumericCellValue();
            Bug b = new Bug();
            b.setCalcType(EmpConstants.RECBUG);
            b.setBugCount((int)bcount);
            b.setBugLevel(-1);
            b.setDeployVersion(deployVersion);
            bugs.add(b);
            for(int level=1;level<=4;level++){
                double bcount_pro = row.getCell(level+13).getNumericCellValue()+
                        row.getCell(level+13+6).getNumericCellValue()+
                        row.getCell(level+13+12).getNumericCellValue();
                if(bcount_pro==0){
                    continue;
                }
                Bug b_pro = new Bug();
                b_pro.setCalcType(EmpConstants.PROBUG);
                b_pro.setBugCount((int)bcount_pro);
                b_pro.setBugLevel(level);
                b_pro.setDeployVersion(deployVersion);
                bugs.add(b_pro);
            }

            Integer codeCount = (int)row.getCell((short) 6).getNumericCellValue();
            if(temp!=null && scellValue.equals(temp.getName())){


                temp.setCodeCount(codeCount);
                temp.setRequirements(requirements);
                temp.setBugs(bugs);
                continue;//这里可以对temp组长做bug/需求等子表数据做填充
            }else if(leader!=null && scellValue.equals(leader.getName())){
                leader.setCodeCount(codeCount);
                leader.setRequirements(requirements);
                leader.setBugs(bugs);
                continue;//这里可以对项目经理做bug/需求等子表数据做填充
            }
            Employee employee = new Employee();
            employee.setName(scellValue);

            employee.setGroupNo(temp.getGroupNo());
            employee.setCodeCount(codeCount);
            employee.setRequirements(requirements);
            employee.setBugs(bugs);

            temp.getChildren().add(employee);
        }
        return list;
    }

    private List<Employee> dealWithSheet(Workbook workbook,List<Employee> toDolist){
        //得到一个工作表
        Sheet sheet =workbook.getSheet("非车季度工作统计-开发");
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        HashMap<String,Integer> empbugs= new HashMap<>();
        //获得所有数据
        for (int i = 2; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);

            //获得获得第i行第0列的 String类型对象
            Cell cell = row.getCell((short) 0);
            String name =cell.getStringCellValue();

            Integer count = (int)row.getCell((short) 1).getNumericCellValue();
            empbugs.put(name,count);
        }
        for(Employee e:toDolist){
            Integer count =null;
            if((count = empbugs.get(e.getName()))!=null){
                Requirement requirement = new Requirement();

                requirement.setRCount(count);
                requirement.setDeployVersion(deployVersion);
                e.getRequirements().add(requirement);
            }
        }
        return toDolist;
    }
}
