package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.Payslips;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiPayslipsTest {

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
    public void getPayslipsTest() throws IOException {
        System.out.println("@Test UK Payroll - getPayslipsTest");
       
        UUID payRunId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        Payslips response = payrollUkApi.getPaySlips(accessToken, xeroTenantId, payRunId, 1);
        
        assertThat(response.getPaySlips().getFirst().getPaySlipID(),is(equalTo(UUID.fromString("ed3fb9b3-e9a1-44fa-a224-8be49facbbe8"))));
        assertThat(response.getPaySlips().getFirst().getEmployeeID(),is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getPaySlips().getFirst().getPayRunID(),is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
        assertThat(response.getPaySlips().getFirst().getFirstName(),is(equalTo("Mike")));
        assertThat(response.getPaySlips().getFirst().getLastName(),is(equalTo("Johnpitragibigson")));
        assertThat(response.getPaySlips().getFirst().getTotalEarnings(),is(equalTo(480.77)));
        assertThat(response.getPaySlips().getFirst().getGrossEarnings(),is(equalTo(480.77)));
        assertThat(response.getPaySlips().getFirst().getTotalPay(),is(equalTo(386.54)));
        assertThat(response.getPaySlips().getFirst().getTotalEmployerTaxes(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().getFirst().getTotalEmployeeTaxes(),is(equalTo(94.23)));
        assertThat(response.getPaySlips().getFirst().getTotalDeductions(),is(equalTo(9.62)));
        assertThat(response.getPaySlips().getFirst().getTotalReimbursements(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().getFirst().getTotalCourtOrders(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().getFirst().getTotalBenefits(),is(equalTo(9.62)));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getEarningsLineID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getDisplayName(),is(equalTo("Regular Hours")));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getRatePerUnit(),is(equalTo(12.019231)));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getNumberOfUnits(),is(equalTo(40.0)));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getAmount(),is(equalTo(480.77)));
        assertThat(response.getPaySlips().getFirst().getEarningsLines().getFirst().getIsLinkedToTimesheet(),is(equalTo(false)));     
        assertThat(response.getPaySlips().getFirst().getLeaveAccrualLines().getFirst().getLeaveTypeID(),is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getPaySlips().getFirst().getLeaveAccrualLines().getFirst().getNumberOfUnits(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().getFirst().getPaymentLines().getFirst().getPaymentLineID(),is(equalTo(UUID.fromString("08ceb312-07af-4ce0-ae9a-176532f07009"))));
        assertThat(response.getPaySlips().getFirst().getPaymentLines().getFirst().getAmount(),is(equalTo(384.3)));
        assertThat(response.getPaySlips().getFirst().getEmployeeTaxLines().getFirst().getTaxLineID(),is(equalTo(UUID.fromString("e7dc717a-4399-4702-9e15-adb87e568dc9"))));
        assertThat(response.getPaySlips().getFirst().getEmployeeTaxLines().getFirst().getDescription(),is(equalTo("PAYE")));
        assertThat(response.getPaySlips().getFirst().getEmployeeTaxLines().getFirst().getAmount(),is(equalTo(48.6)));
        assertThat(response.getPaySlips().getFirst().getEmployeeTaxLines().getFirst().getGlobalTaxTypeID(),is(equalTo("1")));
        assertThat(response.getPaySlips().getFirst().getEmployeeTaxLines().getFirst().getManualAdjustment(),is(equalTo(false)));
        
        //System.out.println(response.toString());
    }
}
