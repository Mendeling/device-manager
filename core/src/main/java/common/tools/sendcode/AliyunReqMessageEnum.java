package common.tools.sendcode;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum AliyunReqMessageEnum {
	SMSError("isv.SMS_ERROR","短信平台错误"),
	PhoneError("isv.MOBILE_NUMBER_ILLEGAL","手机号码不合法"),
	SendTooMuch("isv.BUSINESS_LIMIT_CONTROL","发送验证码操作过于频繁，请稍后重试"),
	;
	

	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	AliyunReqMessageEnum(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public static String getMessage(String code){
		List<AliyunReqMessageEnum> list =  Arrays.stream(AliyunReqMessageEnum.values())
				.filter((e)->{return StringUtils.equals(e.getCode(), code);})
				.collect(Collectors.toList());
		return Optional.ofNullable(list).isPresent()&&list.size()>0?list.get(0).getMessage():SMSError.getMessage();
	}
}
