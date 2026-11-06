package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.PaymentServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiPaymentServicesTest {

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
    public void createPaymentServiceTest() throws IOException {
        System.out.println("@Test - createPaymentService");
        PaymentServices paymentServices = new PaymentServices();
        PaymentServices response = accountingApi.createPaymentService(accessToken,xeroTenantId,paymentServices, null);

        assertThat(response.getPaymentServices().getFirst().getPaymentServiceID(), is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceName(), is(equalTo("PayUpNow")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
        assertThat(response.getPaymentServices().getFirst().getPayNowText(), is(equalTo("Time To Pay")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceType(), is(equalTo("Custom")));
        assertThat(response.getPaymentServices().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("Payment service could not be found")));
        //System.out.println(response.getPaymentServices().getFirst().toString());
    }

    @Test
    public void getPaymentServicesTest() throws IOException {
        System.out.println("@Test - getPaymentServices");
        PaymentServices response = accountingApi.getPaymentServices(accessToken,xeroTenantId);

        assertThat(response.getPaymentServices().getFirst().getPaymentServiceID(), is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceName(), is(equalTo("PayUpNow")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
        assertThat(response.getPaymentServices().getFirst().getPayNowText(), is(equalTo("Time To Pay")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceType(), is(equalTo("Custom")));
        //System.out.println(response.getPaymentServices().getFirst().toString());
    }
}
