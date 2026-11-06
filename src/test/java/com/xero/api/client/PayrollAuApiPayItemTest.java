package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.PayItem;
import com.xero.models.payrollau.PayItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollAuApiPayItemTest {

	ApiClient defaultClient; 
    PayrollAuApi payrollAuApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrollau.api.url"),null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

    @Test
    public void getPayItemsTest() throws IOException {
        System.out.println("@Test - getPayItemsTest");
        
        PayItems response = payrollAuApi.getPayItems(accessToken, xeroTenantId, null, null, null, null);

        assertThat(response.getPayItems().getEarningsRates().getFirst().getName(), is(equalTo("Ordinary Hours")));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getAccountCode(), is(equalTo("477")));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getTypeOfUnits()  , is(equalTo("Hours")));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getIsExemptFromTax() , is(equalTo(false)));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getIsExemptFromSuper() , is(equalTo(false)));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getIsReportableAsW1() , is(equalTo(false)));        
        assertThat(response.getPayItems().getEarningsRates().getFirst().getEarningsType(), is(equalTo(com.xero.models.payrollau.EarningsType.ORDINARYTIMEEARNINGS)));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getRatePerUnit() , is(equalTo("3")));        
        assertThat(response.getPayItems().getEarningsRates().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T04:53:41Z"))));  
        assertThat(response.getPayItems().getEarningsRates().getFirst().getRateType(), is(equalTo(com.xero.models.payrollau.RateType.RATEPERUNIT)));
        assertThat(response.getPayItems().getEarningsRates().getFirst().getCurrentRecord() , is(equalTo(true))); 
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getDeductionTypeID(), is(equalTo(UUID.fromString("727af5e8-b347-4ae7-85fc-9b82266d0aec"))));
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getName(), is(equalTo("Union Fees/Subscriptions")));        
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getAccountCode(), is(equalTo("850")));        
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getReducesTax(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getReducesSuper(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getIsExemptFromW1(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getDeductionCategory(), is(equalTo(com.xero.models.payrollau.DeductionType.DeductionCategoryEnum.UNIONFEES)));  
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-01-14T21:12:10Z"))));       
        assertThat(response.getPayItems().getDeductionTypes().getFirst().getCurrentRecord(), is(equalTo(true)));      
        assertThat(response.getPayItems().getReimbursementTypes().getFirst().getReimbursementTypeID(), is(equalTo(UUID.fromString("98ba33b2-db5b-4204-bcac-5ddd98d63524"))));
        assertThat(response.getPayItems().getReimbursementTypes().getFirst().getName(), is(equalTo("Travel Costs")));        
        assertThat(response.getPayItems().getReimbursementTypes().getFirst().getAccountCode(), is(equalTo("850")));        
        assertThat(response.getPayItems().getReimbursementTypes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-01-14T21:12:10Z"))));
        assertThat(response.getPayItems().getReimbursementTypes().getFirst().getCurrentRecord(), is(equalTo(true)));      
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("fbcc9dab-6238-43d9-a3f4-d768423fdcfa"))));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getName(), is(equalTo("Annual Leave")));        
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getTypeOfUnits(), is(equalTo("Hours")));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getNormalEntitlement(), is(equalTo(152.00)));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getLeaveLoadingRate(),  is(equalTo(1.0)));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T04:54:13Z"))));
        // System.out.println(response.toString());
    }

    @Test
    public void createPayItemTest() throws IOException {
        System.out.println("@Test - createPayItemTest");
        
        PayItem payItems = new PayItem();        
        //PayItems response = payrollAuApi.createPayItem(accessToken, xeroTenantId,payItems);
        
        // CAN NOT TEST response from createPayItem 
        // because API returns EMPTY JSON object
        //System.out.println(response.toString());
    }
}
