package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.PayRunObject;
import com.xero.models.payrolluk.PayRuns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiPayRunsTest {

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
    public void getPayRunsTest() throws IOException {
        System.out.println("@Test UK Payroll - getPayRunsTest");
       
        int page = 1;
        PayRuns response = payrollUkApi.getPayRuns(accessToken, xeroTenantId, page, null);
        
        assertThat(response.getPayRuns().getFirst().getPayRunID(), is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
        assertThat(response.getPayRuns().getFirst().getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
        assertThat(response.getPayRuns().getFirst().getPayRunType(), is(equalTo(com.xero.models.payrolluk.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRuns().getFirst().getPeriodStartDate(),  is(equalTo(LocalDate.of(2020, 02, 03))));
        assertThat(response.getPayRuns().getFirst().getPaymentDate(),  is(equalTo(LocalDate.of(2020, 02, 10))));
        assertThat(response.getPayRuns().getFirst().getTotalCost(),  is(equalTo(490.39)));
        assertThat(response.getPayRuns().getFirst().getTotalPay(),  is(equalTo(376.92)));
        assertThat(response.getPayRuns().getFirst().getPayRunStatus(), is(equalTo(com.xero.models.payrolluk.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRuns().getFirst().getCalendarType(), is(equalTo(com.xero.models.payrolluk.PayRun.CalendarTypeEnum.WEEKLY)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void getPayRunTest() throws IOException {
        System.out.println("@Test UK Payroll - getPayRunTest");
       
        UUID payRunID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PayRunObject response = payrollUkApi.getPayRun(accessToken, xeroTenantId, payRunID);
        
        assertThat(response.getPayRun().getPayRunID(), is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
        assertThat(response.getPayRun().getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
        assertThat(response.getPayRun().getPayRunType(), is(equalTo(com.xero.models.payrolluk.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRun().getPeriodStartDate(),  is(equalTo(LocalDate.of(2020, 02, 03))));
        assertThat(response.getPayRun().getPeriodEndDate(),  is(equalTo(LocalDate.of(2020, 02, 9))));        
        assertThat(response.getPayRun().getPaymentDate(),  is(equalTo(LocalDate.of(2020, 02, 10))));
        assertThat(response.getPayRun().getTotalCost(),  is(equalTo(490.39)));
        assertThat(response.getPayRun().getTotalPay(),  is(equalTo(376.92)));
        assertThat(response.getPayRun().getPayRunStatus(), is(equalTo(com.xero.models.payrolluk.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRun().getCalendarType(), is(equalTo(com.xero.models.payrolluk.PayRun.CalendarTypeEnum.WEEKLY)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getPaySlipID(), is(equalTo(UUID.fromString("ed3fb9b3-e9a1-44fa-a224-8be49facbbe8"))));
        assertThat(response.getPayRun().getPaySlips().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getPayRun().getPaySlips().getFirst().getFirstName(), is(equalTo("Mike")));
        assertThat(response.getPayRun().getPaySlips().getFirst().getLastName(), is(equalTo("Johnpitragibigson")));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalEarnings(), is(equalTo(480.77)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getGrossEarnings(), is(equalTo(480.77)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalPay(), is(equalTo(386.54)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalEmployerTaxes(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalEmployeeTaxes(), is(equalTo(94.23)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalDeductions(), is(equalTo(9.62)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalReimbursements(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalCourtOrders(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().getFirst().getTotalBenefits(), is(equalTo(9.62)));

        //System.out.println(response.toString());
    }
}
