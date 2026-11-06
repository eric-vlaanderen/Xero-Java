package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.EmployeeStatutoryLeavesSummaries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiEmployeeStatutoryLeavesSummaryTest {

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
    public void getEmployeeStatutoryLeavesSummaryTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeStatutoryLeavesSummaryTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeStatutoryLeavesSummaries response = payrollUkApi.getStatutoryLeaveSummary(accessToken, xeroTenantId, employeeId, false);
        
        assertThat(response.getStatutoryLeaves().getFirst().getEmployeeID(),  is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getStatutoryLeaves().getFirst().getStatutoryLeaveID(),  is(equalTo(UUID.fromString("17f4c3c7-d65c-4572-9118-03798f26f813"))));
        assertThat(response.getStatutoryLeaves().getFirst().getType(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveSummary.TypeEnum.SICK)));
        assertThat(response.getStatutoryLeaves().getFirst().getStartDate(), is(equalTo(LocalDate.of(2020, 03, 28))));
        assertThat(response.getStatutoryLeaves().getFirst().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 01))));
        assertThat(response.getStatutoryLeaves().getFirst().getIsEntitled(), is(equalTo(false)));
        assertThat(response.getStatutoryLeaves().getFirst().getStatus(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveSummary.StatusEnum.PENDING)));

        //System.out.println(response.toString());
    }
}
