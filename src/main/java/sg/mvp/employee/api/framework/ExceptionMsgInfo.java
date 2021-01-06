package sg.mvp.employee.api.framework;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionMsgInfo {
	
	private String field;
	
	private String messageId;
	
	private String message;
	
	private String[] messageParam;
	
	private String lineNo;
	
	private Integer arrNo;
	
	public ExceptionMsgInfo() {
		
	}

	public ExceptionMsgInfo(String field, String messageId, String message, String[] messageParam, String lineNo,
			Integer arrNo) {
		this.field = field;
		this.messageId = messageId;
		this.message = message;
		this.messageParam = messageParam;
		this.lineNo = lineNo;
		this.arrNo = arrNo;
	}
	
	

}
