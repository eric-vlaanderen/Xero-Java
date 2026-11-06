package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollnz.LeavePeriods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollNzApiEmployeeLeavePeriodsTest {

	ApiClient defaultClient; 
    PayrollNzApi payrollNzApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrollnz.api.url"),null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeLeavePeriodsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeavePeriodsTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LocalDate startDate = LocalDate.of(2020, Month.MARCH, 30);
        LocalDate endDate = LocalDate.of(2020, Month.MARCH, 30);
        LeavePeriods response = payrollNzApi.getEmployeeLeavePeriods(accessToken, xeroTenantId, employeeId, startDate, endDate);
        
        assertThat(response.getPeriods().getFirst().getPeriodStartDate(),  is(equalTo(LocalDate.of(2020, 02, 24))));
        assertThat(response.getPeriods().getFirst().getPeriodEndDate(),  is(equalTo(LocalDate.of(2020, 03, 01))));
        assertThat(response.getPeriods().getFirst().getNumberOfUnits() , is(equalTo(24.0)));
        
        //System.out.println(response.toString());
    }
}
