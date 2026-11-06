package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.EmployeeOpeningBalancesObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiEmployeeOpeningBalancesTest {

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
    public void getEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeOpeningBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeOpeningBalancesObject response = payrollUkApi.getEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getOpeningBalances().getStatutoryAdoptionPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutoryMaternityPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutoryPaternityPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutorySharedParentalPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutorySickPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getPriorEmployeeNumber() , is(equalTo(10.0)));
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmployeeOpeningBalancesTest - No Test Case - method works, but response NULL");
    }

    @Test
    public void updateEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - updateEmployeeOpeningBalancesTest No Test Case - method works, but response NULL");
    }
}
