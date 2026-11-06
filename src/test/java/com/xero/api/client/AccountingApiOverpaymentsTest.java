package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Allocations;
import com.xero.models.accounting.Overpayments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiOverpaymentsTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId; 
     
    File body;

    private static boolean setUpIsDone = false;
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("accounting.api.url"),null,null,null,null);
        accountingApi = AccountingApi.getInstance(defaultClient);   
       
        ClassLoader classLoader = getClass().getClassLoader();
        body = new File(classLoader.getResource("helo-heros.jpg").getFile());
       
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
    public void createOverpaymentAllocationsTest() throws IOException {
        System.out.println("@Test - createOverpaymentAllocations");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Allocations allocations = new Allocations();
        Allocations response = accountingApi.createOverpaymentAllocations(accessToken,xeroTenantId,overpaymentID, allocations, false, null);

        // TODO: test validations
        assertThat(response.getAllocations().getFirst().getInvoice().getInvoiceID(), is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
        assertThat(response.getAllocations().getFirst().getAmount(), is(equalTo(1.0)));
        assertThat(response.getAllocations().getFirst().getAmount().toString(), is(equalTo("1.0")));
        assertThat(response.getAllocations().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        //System.out.println(response.getAllocations().getFirst().toString());
    }
    
    @Test
    public void getOverpaymentTest() throws IOException {
        System.out.println("@Test - getOverpayment");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Overpayments response = accountingApi.getOverpayment(accessToken,xeroTenantId,overpaymentID);

        //assertThat(response.getOverpayments().getFirst().getType(), is(equalTo(com.xero.models.accounting.Overpayment.TypeEnum.SPEND-OVERPAYMENT)));
        assertThat(response.getOverpayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getOverpayments().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
        assertThat(response.getOverpayments().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getOverpayments().getFirst().getSubTotal(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().getFirst().getSubTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getOverpayments().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getOverpayments().getFirst().getTotal(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().getFirst().getTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getOverpayments().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-12T22:15:52.890Z"))));
        assertThat(response.getOverpayments().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOverpayments().getFirst().getOverpaymentID(), is(equalTo(UUID.fromString("ed7f6041-c915-4667-bd1d-54c48e92161e"))));
        assertThat(response.getOverpayments().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getOverpayments().getFirst().getRemainingCredit(), is(equalTo(2999.0)));
        assertThat(response.getOverpayments().getFirst().getRemainingCredit().toString(), is(equalTo("2999.0")));        
        assertThat(response.getOverpayments().getFirst().getAllocations().getFirst().getAmount(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().getFirst().getAllocations().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));
        assertThat(response.getOverpayments().getFirst().getAllocations().getFirst().getInvoice().getInvoiceID(), is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
        assertThat(response.getOverpayments().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getOverpayments().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("247dd942-5245-47a7-adb1-4d9ea075b431"))));
        assertThat(response.getOverpayments().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getOverpayments().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/banktransaction/ed7f6041-c915-4667-bd1d-54c48e92161e/Attachments/giphy.gif")));
        assertThat(response.getOverpayments().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getOverpayments().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(495727)));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Broken TV deposit")));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("800")));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(3000.0)));
        //System.out.println(response.getOverpayments().getFirst().toString());
    }
    
    @Test
    public void getOverpaymentsTest() throws IOException {
        System.out.println("@Test - getOverpayments");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer page = null;
        Integer unitdp = null;
        Integer pageSize = null;
        
        Overpayments response = accountingApi.getOverpayments(accessToken,xeroTenantId,ifModifiedSince, where, order, page, unitdp, pageSize);

        assertThat(response.getOverpayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getOverpayments().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
        assertThat(response.getOverpayments().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getOverpayments().getFirst().getSubTotal(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().getFirst().getSubTotal().toString(), is(equalTo("500.0")));
        assertThat(response.getOverpayments().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getOverpayments().getFirst().getTotal(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().getFirst().getTotal().toString(), is(equalTo("500.0")));
        assertThat(response.getOverpayments().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-12T22:08:55.123Z"))));
        assertThat(response.getOverpayments().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOverpayments().getFirst().getOverpaymentID(), is(equalTo(UUID.fromString("098b4dcb-5622-4699-87f8-9d40c4ccceb3"))));
        assertThat(response.getOverpayments().getFirst().getRemainingCredit(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().getFirst().getRemainingCredit().toString(), is(equalTo("500.0")));        
        assertThat(response.getOverpayments().getFirst().getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getOverpayments().getFirst().toString());
    }
}
