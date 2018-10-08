package common.exception;


public class AppException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public AppException(AppExceptionEnum exceptionEnum) {
		 super(exceptionEnum.getMessage());
		 this.code = exceptionEnum.getCode();
	}
	
	public AppException(String code,String message) {
		 super(message);
		 this.code = code;
	}
}
