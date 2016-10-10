package cn.brent.commons.validate;

/**
 * 校验异常
 */
public class ValidateException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public ValidateException(String code,String msg) {
		super(msg);
		this.code=code;
	}
	
	public ValidateException(String msg) {
		this(null, msg);
	}
	
	public ValidateException(Throwable cause) {
		super(cause);
		this.code=cause.getMessage();
	}
	
	public ValidateException(String code,Throwable cause) {
		super(cause);
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}
	
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
