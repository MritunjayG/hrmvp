package sg.mvp.employee.api.application.form;

import lombok.Data;

public abstract class BaseForm {

	private String limit;

	private String offset;

	private String filtering;

	private String sorting;

	public BaseForm() {
	}

	public BaseForm(String limit, String offset, String filtering, String sorting) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.filtering = filtering;
		this.sorting = sorting;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getFiltering() {
		return filtering;
	}

	public void setFiltering(String filtering) {
		this.filtering = filtering;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	@Override
	public String toString() {
		return "BaseForm [limit=" + limit + ", offset=" + offset + ", filtering=" + filtering + ", sorting=" + sorting
				+ "]";
	}

}
