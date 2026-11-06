package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.RepeatingInvoices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiRepeatingInvoicesTest {

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

    @Disabled("Enable when OpenAPI schema is changed to binary wherever octet/stream is used")
    @Test
    public void createRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createRepeatingInvoiceAttachmentByFileName");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.createRepeatingInvoiceAttachmentByFileName(accessToken,xeroTenantId,repeatingInvoiceID, fileName, body, null);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("e078e56c-9a2b-4f6c-a1fa-5d19b0dab611"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }

     @Test
    public void getRepeatingInvoiceTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoice");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        RepeatingInvoices response = accountingApi.getRepeatingInvoice(accessToken,xeroTenantId,repeatingInvoiceID);

        assertThat(response.getRepeatingInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
        assertThat(response.getRepeatingInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getPeriod(), is(equalTo(1)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getUnit(), is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getDueDate(), is(equalTo(10)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getDueDateType(), is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,04,15))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getNextScheduledDateAsDate(), is(equalTo(LocalDate.of(2019,04,15))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,9,30))));         
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getRepeatingInvoices().getFirst().getReference(), is(equalTo("[Week]")));
        assertThat(response.getRepeatingInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getRepeatingInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
        assertThat(response.getRepeatingInvoices().getFirst().getSubTotal(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getSubTotal().toString(), is(equalTo("5000.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getTotalTax(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getTotalTax().toString(), is(equalTo("750.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getTotal(), is(equalTo(5750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getTotal().toString(), is(equalTo("5750.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getRepeatingInvoiceID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().getFirst().getID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getRepeatingInvoices().getFirst().getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getRepeatingInvoices().getFirst().toString());
    }
    
    @Test
    public void getRepeatingInvoiceAttachmentsTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoiceAttachments");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = accountingApi.getRepeatingInvoiceAttachments(accessToken,xeroTenantId,repeatingInvoiceID);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    
    @Test
    public void getRepeatingInvoicesTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoices");
        String where = null;
        String order = null;
        RepeatingInvoices response = accountingApi.getRepeatingInvoices(accessToken,xeroTenantId,where, order);

        assertThat(response.getRepeatingInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
        assertThat(response.getRepeatingInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getPeriod(), is(equalTo(1)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getUnit(), is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getDueDate(), is(equalTo(10)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getDueDateType(), is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,04,15))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getNextScheduledDateAsDate(), is(equalTo(LocalDate.of(2019,04,15))));
        assertThat(response.getRepeatingInvoices().getFirst().getSchedule().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,9,30))));         
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getRepeatingInvoices().getFirst().getReference(), is(equalTo("[Week]")));
        assertThat(response.getRepeatingInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getRepeatingInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
        assertThat(response.getRepeatingInvoices().getFirst().getSubTotal(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getSubTotal().toString(), is(equalTo("5000.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getTotalTax(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getTotalTax().toString(), is(equalTo("750.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getTotal(), is(equalTo(5750.0)));
        assertThat(response.getRepeatingInvoices().getFirst().getTotal().toString(), is(equalTo("5750.0")));
        assertThat(response.getRepeatingInvoices().getFirst().getRepeatingInvoiceID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().getFirst().getID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().getFirst().getHasAttachments(), is(equalTo(true)));
        //System.out.println(response.getRepeatingInvoices().getFirst().toString());
    }
/*
    @Test
    public void updateRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateRepeatingInvoiceAttachmentByFileName");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.updateRepeatingInvoiceAttachmentByFileName(repeatingInvoiceID, fileName, body);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("d086d5f4-9c3d-4edc-a87e-906248eeb652"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    */
}
