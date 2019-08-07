package com.oa.auth.constants;

/**
 * @description: 权限相关常量
 * @author: kk
 * @create: 2018-12-22
 **/
public class SecurityConstants {
   public static String TOKEN_PREFIX = "zhoujin";
   /**
    * 基础角色
    */
   public static String BASE_ROLE = "ROLE_USER";
   /**
    * 编码
    */
   public static String UTF8 = "UTF-8";

   /**
    * JSON 资源
    */
   public static String CONTENT_TYPE = "application/json; charset=utf-8";

   public static int TOKEN_VALID_SECONDS= 3600*12;//token有效期12个小时
}
