package com.kjq.erp.exception;
/**
 * 
 * 业务异常
 *
 */
public class BusinessException extends Exception {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4271891069270997093L;
    
    /**
     * 异常分类Code
     */
    private String resultCode;
    
    /**
     * 异常构造方法
     * @param message 错误消息
     * @param replyCode 异常Code
     */
    public BusinessException(String message, String resultCode) {
    	super(message);
    	this.resultCode = resultCode;
    }
    
    public BusinessException(String message) {
    	super(message);    	
    }
   
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
