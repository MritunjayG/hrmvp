package sg.mvp.employee.api.application.form;

public class GetUsersForm extends BaseForm {
	
	private String minSalary;
	
	private String maxSalary;
	
	public GetUsersForm() {
	}

	public GetUsersForm(String minSalary, String maxSalary) {
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}

	public String getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(String minSalary) {
		this.minSalary = minSalary;
	}

	public String getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(String maxSalary) {
		this.maxSalary = maxSalary;
	}

	@Override
	public String toString() {
		return "GetUsersForm [minSalary=" + minSalary + ", maxSalary=" + maxSalary + "]";
	}

	
	
	
	
	
}
