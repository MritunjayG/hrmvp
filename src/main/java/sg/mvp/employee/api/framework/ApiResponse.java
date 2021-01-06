package sg.mvp.employee.api.framework;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
	private String status;
	private List<ExceptionMsgInfo> msgList;
	private Object result;

	public ApiResponse() {

	}

	public ApiResponse(String status, List<ExceptionMsgInfo> msgList, Object result) {
		this.status = status;
		this.msgList = msgList;
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ExceptionMsgInfo> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<ExceptionMsgInfo> msgList) {
		this.msgList = msgList;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	

}
