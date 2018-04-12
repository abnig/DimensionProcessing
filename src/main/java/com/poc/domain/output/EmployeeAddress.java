package com.poc.domain.output;

public class EmployeeAddress extends BaseOutputObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2337309966136279817L;
	
	private String empAddressId;
	
	private String empId;
	
	private String address;

	public String getEmpAddressId() {
		return empAddressId;
	}

	public void setEmpAddressId(String empAddressId) {
		this.empAddressId = empAddressId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((empAddressId == null) ? 0 : empAddressId.hashCode());
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
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
		EmployeeAddress other = (EmployeeAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (empAddressId == null) {
			if (other.empAddressId != null)
				return false;
		} else if (!empAddressId.equals(other.empAddressId))
			return false;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeAddress [empAddressId=" + empAddressId + ", empId=" + empId + ", address=" + address
				+ ", hashCode()=" + hashCode() + "]";
	}
	
}
