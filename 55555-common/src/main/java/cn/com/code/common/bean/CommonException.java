package cn.com.code.common.bean;


public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public CommonException() {
		super();
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CommonException(String message) {
		super(message);
		
	}

	public CommonException(Throwable cause) {
		super(cause);
		
	}

	public CommonException(HttpStatus httpStatus){
		super(httpStatus.reasonPhraseCN());
		this.code = httpStatus.code();
	}
	
	
}
