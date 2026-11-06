package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.SuperFundProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollAuApiSuperfundProductTest {

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
    public void getSuperfundProductsTest() throws IOException {
        System.out.println("@Test - getSuperfundProductsTest");
        
        SuperFundProducts response = payrollAuApi.getSuperfundProducts(accessToken, xeroTenantId, null, null);
        
        assertThat(response.getSuperFundProducts().getFirst().getABN(), is(equalTo("24248426878")));        
        assertThat(response.getSuperFundProducts().getFirst().getUSI(), is(equalTo("OSF0001AU")));        
        assertThat(response.getSuperFundProducts().getFirst().getProductName(), is(equalTo("Accumulate Plus (Commonwealth Bank Group Super)")));        
       //System.out.println(response.toString());
    }
}
