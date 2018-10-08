package common;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
  **********************************
	* @Description: TODO
	* @author: langjun
	* @createdAt: 2017年10月12日下午5:56:29
	**********************************
 */
public class SysConst {
	
	/**
	 * 是否过期
	 */
	public enum Expired{
		OUT_DUE(0),IN_DUE(1);
		private Integer value;
		Expired(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum Publish{
		IS_NOT_PUBLISH(0),IS_PUBLISH(1);
		private Integer value;
		Publish(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum Gender{
		WEMAN(0),MAN(1);
		private Integer value;
		Gender(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum Forbidden{
		IS_FORBIDDEN(0),IS_NOT_FORBIDDEN(1);
		private Integer value;
		Forbidden(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum FirstLoginTip{
		HAS_NOT_LOGIN(0),HAS_LOGIN(1);
		private Integer value;
		FirstLoginTip(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum RoleType{
		ADMIN(0),TEACHER(1),STUDENT(2);
		private Integer value;
		RoleType(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum RegisterType{
		PHONE(0),EMAIL(1),WECHAT(2);
		private Integer value;
		RegisterType(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	
	public enum DeleteFlag{
		NOTDELETE(0),DELETE(1);
		private Integer value;
		DeleteFlag(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
	}
	/**
	 * 排序方式
	 * @author Administrator
	 *
	 */
	public enum SortBy{
		正序("ASC"),倒序("DESC");
		private String value;
		SortBy(String value){
			this.value = value;
		}
		public String getCode(){
			return this.value;
		}
	}
	public enum TokenType{
		TIMEOUT(0),FOREVER(1);
		private Integer value;
		TokenType(Integer value){
			this.value = value;
		}
		public Integer getCode(){
			return this.value;
		}
		public static boolean contain(Integer code){
			if(Optional.ofNullable(code).isPresent()){
				return Arrays.asList(TokenType.values()).stream().map(TokenType::getCode).collect(Collectors.toList()).contains(code);
			}
			return false;
		}
		public static void main(String[] args) {
			System.out.println(TokenType.contain(1));
		}
	}

}
