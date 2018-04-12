package com.poc.domain.output;

import java.math.BigDecimal;

public class EmployeeSalary extends BaseOutputObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8930398419185210746L;
	
	private String empSalaryId;
	
	private String empId;
	
	private BigDecimal salary;

	public String getEmpSalaryId() {
		return empSalaryId;
	}

	public void setEmpSalaryId(String empSalaryId) {
		this.empSalaryId = empSalaryId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((empSalaryId == null) ? 0 : empSalaryId.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeSalary other = (EmployeeSalary) obj;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		if (empSalaryId == null) {
			if (other.empSalaryId != null)
				return false;
		} else if (!empSalaryId.equals(other.empSalaryId))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeSalary [empSalaryId=" + empSalaryId + ", empId=" + empId + ", salary=" + salary
				+ ", hashCode()=" + hashCode() + "]";
	}
	
}
