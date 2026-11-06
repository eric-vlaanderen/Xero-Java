package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.TaxRates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiTaxRatesTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId;  

    private static boolean setUpIsDone = false;
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";

        defaultClient = new ApiClient(ConfigurationLoader.getProperty("accounting.api.url"),null,null,null,null);
        accountingApi = AccountingApi.getInstance(defaultClient);   

        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 60 seconds");
            Thread.sleep(60);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        // do the setup
        setUpIsDone = true;
	}

	public void tearDown() {
		accountingApi = null;
        defaultClient = null;
	}

    @Test
    public void createTaxRatesTest() throws IOException {
        System.out.println("@Test - createTaxRates");
        TaxRates taxRates = new TaxRates();
        TaxRates response = accountingApi.createTaxRates(accessToken,xeroTenantId,taxRates,null);

        assertThat(response.getTaxRates().getFirst().getName(), is(equalTo("SDKTax29067")));
        assertThat(response.getTaxRates().getFirst().getTaxType(), is(equalTo("TAX002")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getName(), is(equalTo("State Tax")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.ACTIVE)));
        assertThat(response.getTaxRates().getFirst().getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate().toString(), is(equalTo("2.25")));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate().toString(), is(equalTo("2.25")));
        //System.out.println(response.getTaxRates().getFirst().toString());
    }

    @Test
    public void getTaxRatesTest() throws IOException {
        System.out.println("@Test - getTaxRates");
        String where = null;
        String order = null;
        
        TaxRates response = accountingApi.getTaxRates(accessToken, xeroTenantId,where, order);

        assertThat(response.getTaxRates().getFirst().getName(), is(equalTo("15% GST on Expenses")));
        assertThat(response.getTaxRates().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getName(), is(equalTo("GST")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.ACTIVE)));
        assertThat(response.getTaxRates().getFirst().getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate().toString(), is(equalTo("15.0")));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate().toString(), is(equalTo("15.0")));
        //System.out.println(response.getTaxRates().getFirst().toString());
    }

    @Test
    public void updateTaxRateTest() throws IOException {
        System.out.println("@Test - updateTaxRate");
        TaxRates taxRates = new TaxRates();
        TaxRates response = accountingApi.updateTaxRate(accessToken,xeroTenantId,taxRates,null);

        assertThat(response.getTaxRates().getFirst().getName(), is(equalTo("SDKTax29067")));
        assertThat(response.getTaxRates().getFirst().getTaxType(), is(equalTo("TAX002")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getName(), is(equalTo("State Tax")));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getTaxComponents().getFirst().getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.DELETED)));
        assertThat(response.getTaxRates().getFirst().getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().getFirst().getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getDisplayTaxRate().toString(), is(equalTo("2.25")));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().getFirst().getEffectiveRate().toString(), is(equalTo("2.25")));
        //System.out.println(response.getTaxRates().getFirst().toString());
    }
}
