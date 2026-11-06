package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.EmployeeLeaveBalances;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiEmployeeLeaveBalancesTest {

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
    public void getEmployeeLeaveBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeLeaveBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveBalances response = payrollUkApi.getEmployeeLeaveBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getPagination().getPage() , is(equalTo(1)));
        assertThat(response.getPagination().getPageSize() , is(equalTo(100)));
        assertThat(response.getPagination().getPageCount() , is(equalTo(1)));
        assertThat(response.getPagination().getItemCount() , is(equalTo(2)));
        assertThat(response.getLeaveBalances().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeaveBalances().getFirst().getName() , is(equalTo("Holiday")));
        assertThat(response.getLeaveBalances().getFirst().getBalance() , is(equalTo(32.0)));
        assertThat(response.getLeaveBalances().getFirst().getTypeOfUnits() , is(equalTo("Hours")));
      
        //System.out.println(response.toString());
    }
}
