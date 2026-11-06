package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiSettingsTest {

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
    public void getSettingsTest() throws IOException {
        System.out.println("@Test UK Payroll - getSettingsTest");
       
        Settings response = payrollUkApi.getSettings(accessToken, xeroTenantId);
        
        assertThat(response.getSettings().getAccounts().getFirst().getAccountID(), is(equalTo(UUID.fromString("9ee28149-32a9-4661-8eab-a28738696983"))));
        assertThat(response.getSettings().getAccounts().getFirst().getType(), is(equalTo(com.xero.models.payrolluk.Account.TypeEnum.WAGESPAYABLE)));
        assertThat(response.getSettings().getAccounts().getFirst().getCode() , is(equalTo("814")));
        assertThat(response.getSettings().getAccounts().getFirst().getName() , is(equalTo("Wages Payable - Payroll")));

        //System.out.println(response.toString());
    }
}
