package com.oa.auth.compute.constants;

import java.math.BigDecimal;

/**
 * @description: 员工信息
 * @author: kk
 * @create: 2018-12-23
 **/
public class EmpConstants {
    public static int Employee = 3;
    public static int GroupLeader=2;
    public static int ProjectLeader=1;
    public static int DevlopmentLeader=0;
    public static BigDecimal recbugLevel1Cof=new BigDecimal(0.25);
    public static BigDecimal recbugLevel2Cof=new BigDecimal(0.25);
    public static BigDecimal recbugLevel3Cof=new BigDecimal(0.25);
    public static BigDecimal recbugLevel4Cof=new BigDecimal(0.25);
    public static BigDecimal probugLevel1Cof=new BigDecimal(-0.4);
    public static BigDecimal probugLevel2Cof=new BigDecimal(-0.3);
    public static BigDecimal probugLevel3Cof=new BigDecimal(-0.2);
    public static BigDecimal probugLevel4Cof=new BigDecimal(-0.1);
    public static BigDecimal rLevel1Cof=new BigDecimal(5);
    public static BigDecimal rLevel2Cof=new BigDecimal(4);
    public static BigDecimal rLevel3Cof=new BigDecimal(1);

    public static Integer PROBUG=1;
    public static Integer RECBUG=0;

    public static BigDecimal rbv=BigDecimal.ONE;
    public static BigDecimal managerGroupCoff = new BigDecimal(0.3);
    public static BigDecimal managerSelfCoff = new BigDecimal(1);
}
