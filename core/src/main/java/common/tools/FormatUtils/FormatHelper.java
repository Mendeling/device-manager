package common.tools.FormatUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import common.exception.AppException;
import common.exception.AppExceptionEnum;


@Slf4j
public class FormatHelper {

	public static final void phoneNumberCheck(String phone) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if(!Optional.ofNullable(phone).isPresent()||phone.length() != 11){
            throw new AppException(AppExceptionEnum.PhoneNumberError);
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if(isMatch){
                log.debug("您的手机号" + phone + "是正确格式@——@");
            } else {
            	 throw new AppException(AppExceptionEnum.PhoneFormatError);
            }
        }
	}
}
