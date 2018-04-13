package com.dimframework.invoker;

import java.util.Date;

public interface DimensionProcessInvoker {
	
	public void invoker(String domainName, Date effectiveStartDate, Date effectiveEndDate);
	
}
