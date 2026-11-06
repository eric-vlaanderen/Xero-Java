package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.PurchaseOrders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiPurchaseOrdersTest {

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

   /* @Test
    public void createPurchaseOrderTest() throws IOException {
        System.out.println("@Test - createPurchaseOrder");
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        PurchaseOrders response = accountingApi.createPurchaseOrder(accessToken,xeroTenantId,purchaseOrder);

        // TODO: test validations
        assertThat(response.getPurchaseOrders().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("792b7e40-b9f2-47f0-8624-b09f4b0166dd"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("710")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getPurchaseOrders().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderNumber(), is(equalTo("PO-0004")));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderID(), is(equalTo(UUID.fromString("56204648-8fbe-46f8-b09c-2125f7939533"))));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax().toString(), is(equalTo("3.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotal(), is(equalTo(23.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotal().toString(), is(equalTo("23.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().getFirst().getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:22:26.077-07:00"))));  
        assertThat(response.getPurchaseOrders().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("Order number must be unique")));
        assertThat(response.getPurchaseOrders().getFirst().getWarnings().getFirst().getMessage(), is(equalTo("Only AUTHORISED and BILLED purchase orders may have SentToContact updated.")));
        //System.out.println(response.getPurchaseOrders().getFirst().toString());
    }
    */

    @Test
    public void getPurchaseOrderTest() throws IOException {
        System.out.println("@Test - getPurchaseOrder");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        PurchaseOrders response = accountingApi.getPurchaseOrder(accessToken,xeroTenantId,purchaseOrderID);

        assertThat(response.getPurchaseOrders().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("8a9d3eca-e052-43bc-9b87-221d0648c045"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Brand new Fender Strats")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(2500.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getItemCode(), is(equalTo("123")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("630")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(337.5)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(2250.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTracking().getFirst().getName(), is(equalTo("Simpsons")));
        assertThat(response.getPurchaseOrders().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,26))));  
        assertThat(response.getPurchaseOrders().getFirst().getDeliveryDateAsDate(), is(equalTo(LocalDate.of(2019,03,28))));  
        assertThat(response.getPurchaseOrders().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderNumber(), is(equalTo("PO-0006")));
        assertThat(response.getPurchaseOrders().getFirst().getReference(), is(equalTo("foobar")));
        assertThat(response.getPurchaseOrders().getFirst().getBrandingThemeID(), is(equalTo(UUID.fromString("414d4a87-46d6-4cfc-ab42-4e29d22e5076"))));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().getFirst().getDeliveryAddress(), is(equalTo("101 Grafton Road\nRoseneath\nWellington\n6011\nNew Zealand")));
        assertThat(response.getPurchaseOrders().getFirst().getAttentionTo(), is(equalTo("CEO")));
        assertThat(response.getPurchaseOrders().getFirst().getTelephone(), is(equalTo("64 123-2222")));
        assertThat(response.getPurchaseOrders().getFirst().getDeliveryInstructions(), is(equalTo("Drop off at front  door")));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderID(), is(equalTo(UUID.fromString("15369a9f-17b6-4235-83c4-0029256d1c37"))));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal(), is(equalTo(2250.0)));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal().toString(), is(equalTo("2250.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax(), is(equalTo(337.5)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax().toString(), is(equalTo("337.5")));
        assertThat(response.getPurchaseOrders().getFirst().getTotal(), is(equalTo(2587.5)));
        assertThat(response.getPurchaseOrders().getFirst().getTotal().toString(), is(equalTo("2587.5")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount(), is(equalTo(250.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount().toString(), is(equalTo("250.0")));
        assertThat(response.getPurchaseOrders().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getPurchaseOrders().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-26T18:47:09.823Z"))));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("7d94ccdc-ef7b-4806-87ac-8442f25e593b"))));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.png")));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/png")));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/PurchaseOrders/15369a9f-17b6-4235-83c4-0029256d1c37/Attachments/HelloWorld.png")));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(76091)));
        assertThat(response.getPurchaseOrders().getFirst().getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));        
        //System.out.println(response.getPurchaseOrders().getFirst().toString());
    }
    
    
    @Test
    public void getPurchaseOrdersTest() throws IOException {
        System.out.println("@Test - getPurchaseOrders");
        OffsetDateTime ifModifiedSince = null;
        String status = null;
        String dateFrom = null;
        String dateTo = null;
        String order = null;
        Integer page = null;
        Integer pageSize = null;
        PurchaseOrders response = accountingApi.getPurchaseOrders(accessToken,xeroTenantId,ifModifiedSince, status, dateFrom, dateTo, order, page, pageSize);

        assertThat(response.getPurchaseOrders().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("0f7b54b8-bfa4-4c5d-9c22-73dbd5796e54"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getPurchaseOrders().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderNumber(), is(equalTo("PO-0001")));
        assertThat(response.getPurchaseOrders().getFirst().getAttentionTo(), is(equalTo("Jimmy")));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DELETED)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderID(), is(equalTo(UUID.fromString("f9627f0d-b715-4039-bb6a-96dc3eae5ec5"))));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotal(), is(equalTo(20.0)));       
        assertThat(response.getPurchaseOrders().getFirst().getTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:18:23.443Z"))));
        //System.out.println(response.getPurchaseOrders().getFirst().toString());
    }
    
    @Test
    public void updatePurchaseOrderTest() throws IOException {
        System.out.println("@Test - updatePurchaseOrder");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        PurchaseOrders purchaseOrders = new PurchaseOrders();
        PurchaseOrders response = accountingApi.updatePurchaseOrder(accessToken,xeroTenantId,purchaseOrderID, purchaseOrders, null);

        assertThat(response.getPurchaseOrders().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("d1d9b2cd-c9f2-4445-8d98-0b8096cf4dae"))));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("710")));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getPurchaseOrders().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderNumber(), is(equalTo("PO-0005")));
        assertThat(response.getPurchaseOrders().getFirst().getAttentionTo(), is(equalTo("Jimmy")));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().getFirst().getPurchaseOrderID(), is(equalTo(UUID.fromString("f9fc1120-c937-489e-84bc-e822190cfe9c"))));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().getFirst().getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalTax().toString(), is(equalTo("3.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotal(), is(equalTo(23.0)));       
        assertThat(response.getPurchaseOrders().getFirst().getTotal().toString(), is(equalTo("23.0")));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().getFirst().getTotalDiscount().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:39:36.853Z"))));
        //System.out.println(response.getPurchaseOrders().getFirst().toString());
    }
}
