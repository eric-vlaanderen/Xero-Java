package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.Receipts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiReceiptsTest {

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
    public void createReceiptTest() throws IOException {
        System.out.println("@Test - createReceipt");
        Receipts receipts = new Receipts();
        Receipts response = accountingApi.createReceipt(accessToken,xeroTenantId,receipts, 4, null);

        assertThat(response.getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,14))));  
        assertThat(response.getReceipts().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().getFirst().getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:49:43.367Z"))));
        assertThat(response.getReceipts().getFirst().getHasAttachments(), is(equalTo(false)));
        assertThat(response.getReceipts().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("A valid user should be identified using the UserID.")));
        //System.out.println(response.getReceipts().getFirst().toString());
    }
    
    @Disabled("Enable when OpenAPI schema is changed to binary wherever octet/stream is used")
    @Test
    public void createReceiptAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createReceiptAttachmentByFileName");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.createReceiptAttachmentByFileName(accessToken,xeroTenantId,receiptID, fileName, body, null);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("3451e34c-66a6-42b0-91e2-88618bdc169b"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    

    @Test
    public void getReceiptTest() throws IOException {
        System.out.println("@Test - getReceipt");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Receipts response = accountingApi.getReceipt(accessToken,xeroTenantId,receiptID,4);

        assertThat(response.getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getReceipts().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().getFirst().getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:49:43.983Z"))));
        assertThat(response.getReceipts().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("e02a84f6-b83a-4983-b3b9-35cd8880c7bc"))));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/HelloWorld.jpg")));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getReceipts().getFirst().getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getReceipts().getFirst().toString());
    }
    
    @Test
    public void getReceiptAttachmentsTest() throws IOException {
        System.out.println("@Test - getReceiptAttachments");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = accountingApi.getReceiptAttachments(accessToken,xeroTenantId,receiptID);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("11e5ca6b-d38c-42ab-a29f-c1710d171aa1"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/7923c00d-163d-404c-a608-af3de333db29/Attachments/giphy.gif")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(495727)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }

    
    @Test
    public void getReceiptsTest() throws IOException {
        System.out.println("@Test - getReceipts");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer unitdp = null;
        Receipts response = accountingApi.getReceipts(accessToken,xeroTenantId,ifModifiedSince, where, order,unitdp);

        assertThat(response.getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getReceipts().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().getFirst().getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T00:49:43.983Z"))));
        assertThat(response.getReceipts().getFirst().getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getReceipts().getFirst().toString());
    }
    
    @Test
    public void updateReceiptTest() throws IOException {
        System.out.println("@Test - updateReceipt");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Receipts receipts = new Receipts();
        Receipts response = accountingApi.updateReceipt(accessToken,xeroTenantId,receiptID, receipts, 4, null);

        assertThat(response.getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,16))));  
        assertThat(response.getReceipts().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().getFirst().getReference(), is(equalTo("Foobar")));
        assertThat(response.getReceipts().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("e3686fdc-c661-4581-b9df-cbb20782ea66"))));
        assertThat(response.getReceipts().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().getFirst().getReceiptNumber(), is(equalTo("2")));
        assertThat(response.getReceipts().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-15T18:45:56.927Z"))));
        assertThat(response.getReceipts().getFirst().getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getReceipts().getFirst().toString());
    }
    /*
    @Test
    public void updateReceiptAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateReceiptAttachmentByFileName");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.updateReceiptAttachmentByFileName(receiptID, fileName, body);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("e02a84f6-b83a-4983-b3b9-35cd8880c7bc"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    */
}
