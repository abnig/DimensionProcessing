package com.poc.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.poc.domain.input.EmployeeInputData;
import com.poc.domain.output.BaseOutputObject;
import com.poc.domain.output.Department;
import com.poc.domain.output.Employee;
import com.poc.domain.output.EmployeeAddress;
import com.poc.domain.output.EmployeeSalary;

public class EmployeeInputDataProcessor extends BaseItemProcessor<EmployeeInputData, BaseOutputObject> {

	@Override
	public List<BaseOutputObject> process(EmployeeInputData employeeInputData) throws Exception {
		List<BaseOutputObject> list = new ArrayList<>();
		Department d = new Department();
		d.setDepartment(employeeInputData.getDepartment());
		d.setDepartmentId(UUID.randomUUID().toString());
		
		Employee e = new Employee();
		e.setEmpId(UUID.randomUUID().toString());
		e.setFirstName(employeeInputData.getFirstName());
		e.setLastName(employeeInputData.getLastName());
		e.setDeptId(d.getDepartmentId());
		
		EmployeeAddress ea = new EmployeeAddress();
		ea.setAddress(employeeInputData.getAddress());
		ea.setEmpAddressId(UUID.randomUUID().toString());
		ea.setEmpId(e.getEmpId());
		
		EmployeeSalary es = new EmployeeSalary();
		es.setEmpId(e.getEmpId());
		es.setEmpSalaryId(UUID.randomUUID().toString());
		es.setSalary(employeeInputData.getSalary());
		
		list.add(es);
		list.add(ea);
		list.add(e);
		list.add(d);

		return list;
	}

}
