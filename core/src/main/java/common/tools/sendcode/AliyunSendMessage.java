package common.tools.sendcode;

import java.rmi.ServerException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import common.exception.AppException;
import common.exception.AppExceptionEnum;
/**
 * 
 * @author seven
 *
 */ 
public class AliyunSendMessage {
	private static final Logger logger = LoggerFactory.getLogger(AliyunSendMessage.class);
	private final static int VALIDATE_CODE_LENGTH = 6; //验证码长度
	//初始化ascClient需要的几个参数
	private static final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	private static final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	//替换成你的AK
	private static final String accessKeyId = "LTAIXsMlAI2MT85W";//你的accessKeyId,参考本文档步骤2
	private static final String accessKeySecret = "4w5q1jPs5cLoWKkgSQE4RsUpHQbEHI";//你的accessKeySecret，参考本文档步骤2
	
	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	public static String getValidateCode() {
		String code = "";
		String nanoTime = System.nanoTime() + "";
		int count = 0;
		for (int i = nanoTime.length() - 1; i >= 0; i--) {
			code += nanoTime.charAt(i);
			count++;
			if (count == VALIDATE_CODE_LENGTH) {
				break;
			}
		}
		return code;
	}

	
	public static boolean SendRegisterMessage(String code,String phone){
		try {
			
			//设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,  domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			 //组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			 //使用post提交
			request.setMethod(MethodType.POST);
			 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(phone);//接收号码
			 //必填:短信签名-可在短信控制台中找到
			request.setSignName("翟纪龙");//控制台创建的签名名称
			request.setTemplateCode("SMS_133970253");//控制台创建的模板CODE
			request.setTemplateParam("{\"code\":\""+code+"\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
			//request.setParamString("{}");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			String backData = JSONObject.toJSONString(sendSmsResponse);
			
			boolean succ = true;
			if( !sendSmsResponse.getCode().equals("OK")){
				logger.error(sendSmsResponse.getCode(),sendSmsResponse.getMessage());
				throw new AppException(AppExceptionEnum.PhoneCodeSendFail.getCode(),AliyunReqMessageEnum.getMessage(sendSmsResponse.getCode()));
			}
					
			logger.info("phoneNum:"+phone+"  backdata:"+JSONObject.toJSONString(sendSmsResponse));
			return succ;
		} catch (ClientException e) {
			logger.error(e.getMessage(),e);
			throw new AppException(AppExceptionEnum.PhoneCodeSendFail.getCode(),String.format(AppExceptionEnum.PhoneCodeSendFail.getMessage(), phone));
		}
	}
	public static void main(String[] args) {
		SendRegisterMessage("1111","18515588371");

	}

}
