package org.blazer.groupreport.model;

import java.math.BigDecimal;

public class FinanceKpi {

	private Integer period_key;
	private Integer time_key;
	private Integer version_key;
	private String product_type;
	private String time_limit;
	private String product_name;
	private BigDecimal transaction_money;
	private Integer transaction_num;

	public Integer getPeriod_key() {
		return period_key;
	}

	public void setPeriod_key(Integer period_key) {
		this.period_key = period_key;
	}

	public Integer getTime_key() {
		return time_key;
	}

	public void setTime_key(Integer time_key) {
		this.time_key = time_key;
	}

	public Integer getVersion_key() {
		return version_key;
	}

	public void setVersion_key(Integer version_key) {
		this.version_key = version_key;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public BigDecimal getTransaction_money() {
		return transaction_money;
	}

	public void setTransaction_money(BigDecimal transaction_money) {
		this.transaction_money = transaction_money;
	}

	public Integer getTransaction_num() {
		return transaction_num;
	}

	public void setTransaction_num(Integer transaction_num) {
		this.transaction_num = transaction_num;
	}

	@Override
	public String toString() {
		return "FinanceKpi [period_key=" + period_key + ", time_key=" + time_key + ", version_key=" + version_key + ", product_type=" + product_type
				+ ", time_limit=" + time_limit + ", product_name=" + product_name + ", transaction_money=" + transaction_money + ", transaction_num="
				+ transaction_num + "]";
	}

}
