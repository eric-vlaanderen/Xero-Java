package com.xero.api.client;


import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.BrandingThemes;
import com.xero.models.accounting.PaymentService;
import com.xero.models.accounting.PaymentServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiBrandingThemeTest {

    private static boolean setUpIsDone = false;
    ApiClient defaultClient;
    AccountingApi accountingApi;
    String accessToken;
    String xeroTenantId;

    @BeforeEach
    public void setUp() {
        // Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";

        defaultClient = new ApiClient(ConfigurationLoader.getProperty("accounting.api.url"), null, null, null, null);
        accountingApi = AccountingApi.getInstance(defaultClient);

        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 60 seconds");
            Thread.sleep(60);
        } catch (InterruptedException e) {
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
    public void testGetBrandingThemes() throws Exception {
        System.out.println("@Test - getBrandingThemes");

        BrandingThemes bt = accountingApi.getBrandingThemes(accessToken, xeroTenantId);
        assertThat(bt.getBrandingThemes().getFirst().getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
        assertThat(bt.getBrandingThemes().getFirst().getName(), is(equalTo("Standard")));
        //System.out.println(bt.toString());
    }

    @Test
    public void testGetBrandingTheme() throws Exception {
        System.out.println("@Test - getBrandingTheme");
        UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

        BrandingThemes oneBrandingTheme = accountingApi.getBrandingTheme(accessToken, xeroTenantId, brandingThemeId);
        assertThat(oneBrandingTheme.getBrandingThemes().getFirst().getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
        assertThat(oneBrandingTheme.getBrandingThemes().getFirst().getName(), is(equalTo("Standard")));
        //System.out.println(bt.toString());
    }

    @Test
    public void testGetBrandingThemePaymentServices() throws Exception {
        System.out.println("@Test - getBrandingThemePaymentServices");
        UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

        PaymentServices paymentServicesForBrandingTheme = accountingApi.getBrandingThemePaymentServices(accessToken, xeroTenantId, brandingThemeId);
        assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceID().toString(), is(equalTo("dede7858-14e3-4a46-bf95-4d4cc491e645")));
        assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceName(), is(equalTo("ACME Payment")));
        //System.out.println(paymentServicesForBrandingTheme.toString());
    }

    @Test
    public void testCreateBrandingThemePaymentServices() throws Exception {
        System.out.println("@Test - createBrandingThemePaymentServices");
        UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
        UUID paymentServiceId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

        PaymentServices btPaymentServices = new PaymentServices();
        PaymentService btPaymentService = new PaymentService();
        btPaymentService.setPaymentServiceID(paymentServiceId);
        btPaymentService.setPaymentServiceName("ACME Payments");
        btPaymentService.setPaymentServiceUrl("http://www.mydomain.com/paymentservice");
        btPaymentService.setPayNowText("Pay Now");
        btPaymentServices.addPaymentServicesItem(btPaymentService);
        PaymentServices response = accountingApi.createBrandingThemePaymentServices(accessToken, xeroTenantId, brandingThemeId, btPaymentServices, null);
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceID().toString(), is(equalTo("00000000-0000-0000-0000-000000000000")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceName(), is(equalTo("ACME Payments")));
        assertThat(response.getPaymentServices().getFirst().getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
        assertThat(response.getPaymentServices().getFirst().getPayNowText(), is(equalTo("Pay Now")));

        //System.out.println(response.toString());
    }
}
