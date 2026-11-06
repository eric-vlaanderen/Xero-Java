package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.EmployeeStatutoryLeaveBalanceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiEmployeeStatutoryLeaveBalancesTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrolluk.api.url"),null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);      
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeStatutoryLeaveBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeStatutoryLeaveBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LocalDate asOfDate = LocalDate.of(2020, Month.MARCH, 30);
        EmployeeStatutoryLeaveBalanceObject response = payrollUkApi.getEmployeeStatutoryLeaveBalances(accessToken, xeroTenantId, employeeId, "Sick", asOfDate);
        
        assertThat(response.getLeaveBalance().getLeaveType(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveBalance.LeaveTypeEnum.SICK)));
        assertThat(response.getLeaveBalance().getUnits(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveBalance.UnitsEnum.HOURS)));
        assertThat(response.getLeaveBalance().getBalanceRemaining(), is(equalTo(1120.0)));
        
        //System.out.println(response.toString());
    }
}
