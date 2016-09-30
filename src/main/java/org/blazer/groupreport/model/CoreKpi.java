package org.blazer.groupreport.model;

import java.math.BigDecimal;

public class CoreKpi {

	private String time_name;
	private Integer period_key;
	private Integer time_key;
	private Integer version_key;
	private Integer register_num;
	private BigDecimal fixed_time_money;
	private BigDecimal fund_money;
	private BigDecimal current_money;
	private BigDecimal total_money;

	public String getTime_name() {
		return time_name;
	}

	public void setTime_name(String time_name) {
		this.time_name = time_name;
	}

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

	public Integer getRegister_num() {
		return register_num;
	}

	public void setRegister_num(Integer register_num) {
		this.register_num = register_num;
	}

	public BigDecimal getFixed_time_money() {
		return fixed_time_money;
	}

	public void setFixed_time_money(BigDecimal fixed_time_money) {
		this.fixed_time_money = fixed_time_money;
	}

	public BigDecimal getFund_money() {
		return fund_money;
	}

	public void setFund_money(BigDecimal fund_money) {
		this.fund_money = fund_money;
	}

	public BigDecimal getCurrent_money() {
		return current_money;
	}

	public void setCurrent_money(BigDecimal current_money) {
		this.current_money = current_money;
	}

	public BigDecimal getTotal_money() {
		return total_money;
	}

	public void setTotal_money(BigDecimal total_money) {
		this.total_money = total_money;
	}

	@Override
	public String toString() {
		return "CoreKpi [period_key=" + period_key + ", time_key=" + time_key + ", version_key=" + version_key + ", register_num=" + register_num
				+ ", fixed_time_money=" + fixed_time_money + ", fund_money=" + fund_money + ", current_money=" + current_money + "]";
	}

}
