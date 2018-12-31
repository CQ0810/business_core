package com.tio.app.common.utils;

/**
 * @author likui
 * @create 2018-12-27
 **/
public class RegularUtil {

    private static String PHONE_NUMBER_REG  ="^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    private static  String ID_ENTITY_CARD="^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";

    /**
     * 正则验证手机号
     * @param phone
     * @return
     */
    public static boolean RegularPhone(String phone){
        if(phone.matches(PHONE_NUMBER_REG )){
            return true;
        }else{
            return  false;
        }
    }

    /**
     * 正则验证身份证
     * @param IDCard
     * @return
     */
    public static boolean RegularCard(String IDCard){
        if(IDCard.matches(ID_ENTITY_CARD )){
            return true;
        }else{
            return  false;
        }
    }
}
