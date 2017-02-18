package cn.bigdb.validation.example;

import java.util.Map;

/**
 * У����
 * @author wdmcygah
 *
 */
public class ValidationResult {

	//У�����Ƿ��д�
	private boolean hasErrors;
	
	//У�������Ϣ
	private Map<String,String> errorMsg;

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
				+ errorMsg + "]";
	}

}