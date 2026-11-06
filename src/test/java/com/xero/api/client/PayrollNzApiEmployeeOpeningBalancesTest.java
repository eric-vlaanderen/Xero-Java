package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollnz.EmployeeOpeningBalance;
import com.xero.models.payrollnz.EmployeeOpeningBalancesObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollNzApiEmployeeOpeningBalancesTest {

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
    public void getEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeOpeningBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeOpeningBalancesObject response = payrollNzApi.getEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getOpeningBalances().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().getFirst().getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().getFirst().getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().getFirst().getGrossEarnings(), is(equalTo(40.0)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeOpeningBalancesTest");

        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<EmployeeOpeningBalance> employeeOpeningBalances = new ArrayList<>();
      
        EmployeeOpeningBalancesObject response = payrollNzApi.createEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId, employeeOpeningBalances, null);
      
        assertThat(response.getOpeningBalances().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().getFirst().getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().getFirst().getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().getFirst().getGrossEarnings(), is(equalTo(40.0)));

        //System.out.println(response.toString());
    }
}
