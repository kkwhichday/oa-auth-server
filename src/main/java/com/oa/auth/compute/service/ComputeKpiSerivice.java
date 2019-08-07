package com.oa.auth.compute.service;

import com.oa.auth.compute.constants.EmpConstants;
import com.oa.auth.compute.model.KpiResult;
import com.oa.auth.compute.vo.Bug;
import com.oa.auth.compute.vo.Employee;
import com.oa.auth.compute.vo.Requirement;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 计算kpi得分
 * @author: kk
 * @create: 2019-01-03
 **/
@Service
public class ComputeKpiSerivice {
    public void compute(List<Employee> list) {

        list.forEach(emp -> {
            if (emp.getChildren() != null && emp.getChildren().size() > 0) {
                compute(emp.getChildren());
                BigDecimal min = emp.getChildren().get(0).getWorkLoad();
                BigDecimal sum = BigDecimal.ZERO;
                for (Employee child : emp.getChildren()) {
                    if (min.compareTo(child.getWorkLoad()) > 0) {
                        min = child.getWorkLoad();
                    }
                    sum = sum.add(child.getWorkLoad());
                }

                computeSingleEmp(emp);
                //组内其他员工的工作量的平均数+0.3*内工作量最小的那个人的工作量+0.1*自己的工作量
                emp.setWorkLoad(sum.divide(new BigDecimal(emp.getChildren().size()),2,BigDecimal.ROUND_HALF_UP)
                        .add(min.multiply(EmpConstants.managerGroupCoff))
                        .add(emp.getWorkLoad().multiply(EmpConstants.managerSelfCoff))
                        .setScale(2, BigDecimal.ROUND_HALF_UP));

            } else {

                computeSingleEmp(emp);

            }
        });
    }

    private void computeSingleEmp(Employee emp) {
        BigDecimal bwork = BigDecimal.ZERO;
        Integer probugs = 0;
        Integer reqs = 0;
        for (Bug bug : emp.getBugs()){
            if (bug.getCalcType() == EmpConstants.PROBUG) {
                probugs =probugs+bug.getBugCount();
            }
        }
        emp.setBugCount(probugs);
        for (Requirement reqt : emp.getRequirements()){

            reqs = reqs+reqt.getRCount();
        }
        emp.setReqCount(reqs);
        BigDecimal brv = new BigDecimal(probugs).divide(new BigDecimal(reqs),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal commonBrv = BigDecimal.ONE.divide(EmpConstants.rbv,2,BigDecimal.ROUND_HALF_UP);
        if(brv.compareTo(commonBrv)>0){
            brv = brv.divide(commonBrv,2,BigDecimal.ROUND_HALF_UP);
//            brv = new BigDecimal(Math.sqrt(brv.doubleValue()));
        }else{
            brv = BigDecimal.ONE;
        }

        System.out.println(emp.getName()+" brv="+brv+" " +"common="+BigDecimal.ONE.divide(EmpConstants.rbv,2,BigDecimal.ROUND_HALF_UP)+" "

                +brv.multiply(EmpConstants.rbv).setScale(2,BigDecimal.ROUND_HALF_UP));
        for (Bug bug : emp.getBugs()) {
            if (bug.getCalcType() == EmpConstants.RECBUG) {
                switch (bug.getBugLevel()) {
                    case -1://对于修改bug，暂时不考虑bug等级
                        bwork = bwork.add(EmpConstants.recbugLevel1Cof.multiply(new BigDecimal(bug.getBugCount())));
                        break;
                    case 1:
                        bwork = bwork.add(EmpConstants.recbugLevel1Cof.multiply(new BigDecimal(bug.getBugCount())));
                        break;
                    case 2:
                        bwork = bwork.add(EmpConstants.recbugLevel2Cof.multiply(new BigDecimal(bug.getBugCount())));
                        break;
                    case 3:
                        bwork = bwork.add(EmpConstants.recbugLevel3Cof.multiply(new BigDecimal(bug.getBugCount())));
                        break;
                    case 4:
                        bwork = bwork.add(EmpConstants.recbugLevel4Cof.multiply(new BigDecimal(bug.getBugCount())));
                        break;
                    default:
                        bwork = bwork.add(BigDecimal.ZERO);
                }
            } else if (bug.getCalcType() == EmpConstants.PROBUG) {
                switch (bug.getBugLevel()) {
                    case 1:
                        bwork = bwork.add(EmpConstants.probugLevel1Cof.multiply(new BigDecimal(bug.getBugCount())).multiply(brv));
                        break;
                    case 2:
                        bwork = bwork.add(EmpConstants.probugLevel2Cof.multiply(new BigDecimal(bug.getBugCount())).multiply(brv));
                        break;
                    case 3:
                        bwork = bwork.add(EmpConstants.probugLevel3Cof.multiply(new BigDecimal(bug.getBugCount())).multiply(brv));
                        break;
                    case 4:
                        bwork = bwork.add(EmpConstants.probugLevel4Cof.multiply(new BigDecimal(bug.getBugCount())).multiply(brv));
                        break;
                    default:
                        bwork = bwork.add(BigDecimal.ZERO);
                }
            }
        }

        BigDecimal rwork = BigDecimal.ZERO;

        for (Requirement reqt : emp.getRequirements()) {
            switch (reqt.getRLevel()) {
                case 1:
                    rwork = rwork.add(EmpConstants.rLevel1Cof.multiply(new BigDecimal(reqt.getRCount())));
                    break;
                case 2:
                    rwork = rwork.add(EmpConstants.rLevel2Cof.multiply(new BigDecimal(reqt.getRCount())));
                    break;
                case 3:
                    rwork = rwork.add(EmpConstants.rLevel3Cof.multiply(new BigDecimal(reqt.getRCount())));
                    break;
                default:
                    rwork = rwork.add(EmpConstants.rLevel3Cof.multiply(new BigDecimal(reqt.getRCount())));
            }
        }

        emp.setWorkLoad(rwork.multiply(EmpConstants.rbv)
                .add(bwork).setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }
}
