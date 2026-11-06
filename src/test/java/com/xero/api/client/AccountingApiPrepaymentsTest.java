package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Allocations;
import com.xero.models.accounting.Prepayments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiPrepaymentsTest {

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
            System.out.println("Sleep for 30 seconds");
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
    public void createPrepaymentAllocationTest() throws IOException {
        System.out.println("@Test - createPrepaymentAllocation");
        UUID prepaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Allocations allocations = new Allocations();
        Allocations response = accountingApi.createPrepaymentAllocations(accessToken,xeroTenantId,prepaymentID, allocations, false, null);

        assertThat(response.getAllocations().getFirst().getAmount(), is(equalTo(1.0)));
        assertThat(response.getAllocations().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));
        assertThat(response.getAllocations().getFirst().getInvoice().getInvoiceID(), is(equalTo(UUID.fromString("c7c37b83-ac95-45ea-88ba-8ad83a5f22fe"))));
        //System.out.println(response.getAllocations().getFirst().toString());
    }

    
    @Test
    public void getPrepaymentTest() throws IOException {
        System.out.println("@Test - getPrepayment");
        UUID prepaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Prepayments response = accountingApi.getPrepayment(accessToken,xeroTenantId,prepaymentID);

        assertThat(response.getPrepayments().getFirst().getType().toString(), is(equalTo("RECEIVE-PREPAYMENT")));
        assertThat(response.getPrepayments().getFirst().getContact().getName(), is(equalTo("Luke Skywalker")));
        assertThat(response.getPrepayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getPrepayments().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Prepayment.StatusEnum.AUTHORISED)));
        assertThat(response.getPrepayments().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Light Speeder")));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(3000.0)));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(450.0)));
        assertThat(response.getPrepayments().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(3000.0)));
        assertThat(response.getPrepayments().getFirst().getSubTotal(), is(equalTo(3000.0)));
        assertThat(response.getPrepayments().getFirst().getSubTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getPrepayments().getFirst().getTotalTax(), is(equalTo(450.0)));
        assertThat(response.getPrepayments().getFirst().getTotalTax().toString(), is(equalTo("450.0")));
        assertThat(response.getPrepayments().getFirst().getTotal(), is(equalTo(3450.0)));
        assertThat(response.getPrepayments().getFirst().getTotal().toString(), is(equalTo("3450.0")));
        assertThat(response.getPrepayments().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:13:44.850Z"))));
        assertThat(response.getPrepayments().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPrepayments().getFirst().getPrepaymentID(), is(equalTo(UUID.fromString("ce0cddef-cf5a-4e59-b638-f225679115a7"))));
        assertThat(response.getPrepayments().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPrepayments().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPrepayments().getFirst().getRemainingCredit(), is(equalTo(3449.0)));
        assertThat(response.getPrepayments().getFirst().getRemainingCredit().toString(), is(equalTo("3449.0")));
        assertThat(response.getPrepayments().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getPrepayments().getFirst().getAllocations().getFirst().getAmount(), is(equalTo(1.0)));
        assertThat(response.getPrepayments().getFirst().getAllocations().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));
        assertThat(response.getPrepayments().getFirst().getAllocations().getFirst().getInvoice().getInvoiceNumber(), is(equalTo("INV-0004")));
        assertThat(response.getPrepayments().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("2ca06aa0-3629-474a-9401-553d4b7fa9b0"))));        
        assertThat(response.getPrepayments().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getPrepayments().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/banktransaction/ce0cddef-cf5a-4e59-b638-f225679115a7/Attachments/giphy.gif")));
        assertThat(response.getPrepayments().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getPrepayments().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(495727)));
        //System.out.println(response.getPrepayments().getFirst().toString());
    }    
  
    @Test
    public void getPrepaymentsTest() throws IOException {
        System.out.println("@Test - getPrepayments");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer page = null;
        Integer unitdp = null;
        Integer pageSize = null;
        Prepayments response = accountingApi.getPrepayments(accessToken,xeroTenantId,ifModifiedSince, where, order, page, unitdp, pageSize);

        assertThat(response.getPrepayments().getFirst().getType().toString(), is(equalTo("RECEIVE-PREPAYMENT")));
        assertThat(response.getPrepayments().getFirst().getContact().getName(), is(equalTo("Luke Skywalker")));
        assertThat(response.getPrepayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getPrepayments().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Prepayment.StatusEnum.AUTHORISED)));
        assertThat(response.getPrepayments().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPrepayments().getFirst().getSubTotal(), is(equalTo(3000.0)));
        assertThat(response.getPrepayments().getFirst().getSubTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getPrepayments().getFirst().getTotalTax(), is(equalTo(450.0)));
        assertThat(response.getPrepayments().getFirst().getTotalTax().toString(), is(equalTo("450.0")));
        assertThat(response.getPrepayments().getFirst().getTotal(), is(equalTo(3450.0)));
        assertThat(response.getPrepayments().getFirst().getTotal().toString(), is(equalTo("3450.0")));
        assertThat(response.getPrepayments().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-13T14:59:47.730Z"))));
        assertThat(response.getPrepayments().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPrepayments().getFirst().getPrepaymentID(), is(equalTo(UUID.fromString("ce0cddef-cf5a-4e59-b638-f225679115a7"))));
        assertThat(response.getPrepayments().getFirst().getRemainingCredit(), is(equalTo(3450.0)));
        assertThat(response.getPrepayments().getFirst().getRemainingCredit().toString(), is(equalTo("3450.0")));
        assertThat(response.getPrepayments().getFirst().getHasAttachments(), is(equalTo(true)));
        //System.out.println(response.getPrepayments().getFirst().toString());
    }
}
