package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.InvoiceReminders;
import com.xero.models.accounting.Invoices;
import com.xero.models.accounting.OnlineInvoices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiInvoicesTest {

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
    public void createInvoiceTest() throws IOException {
        System.out.println("@Test - createInvoices");
        Invoices invoices = new Invoices();
        Invoices response = accountingApi.createInvoices(accessToken, xeroTenantId, invoices, false, 4, null, null);
     
        assertThat(response.getInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
        assertThat(response.getInvoices().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 3, 11))));
        assertThat(response.getInvoices().getFirst().getDueDateAsDate(), is(equalTo(LocalDate.of(2018, 12, 10))));  
        assertThat(response.getInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getInvoices().getFirst().getInvoiceNumber(), is(equalTo("INV-0007")));
        assertThat(response.getInvoices().getFirst().getReference(), is(equalTo("Website Design")));
        assertThat(response.getInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED)));
        assertThat(response.getInvoices().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getInvoices().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getInvoices().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getInvoices().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getInvoices().getFirst().getInvoiceID(), is(equalTo(UUID.fromString("ed255415-e141-4150-aab7-89c3bbbb851c"))));
        assertThat(response.getInvoices().getFirst().getIsDiscounted(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getInvoices().getFirst().getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:58:46.117Z"))));  
        assertThat(response.getInvoices().getFirst().getHasErrors(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getInvoices().getFirst().getContact().getName(), is(equalTo("Liam Gallagher")));
        assertThat(response.getInvoices().getFirst().getContact().getFirstName(), is(equalTo("Liam")));
        assertThat(response.getInvoices().getFirst().getContact().getLastName(), is(equalTo("Gallagher")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getFirstName(), is(equalTo("Debbie")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getLastName(), is(equalTo("Gwyther")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getEmailAddress(), is(equalTo("debbie@rockstar.com")));
        assertThat(response.getInvoices().getFirst().getContact().getContactGroups().getFirst().getName(), is(equalTo("Oasis")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("5f7a612b-fdcc-4d33-90fa-a9f6bc6db32f"))));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Acme Tires")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(2.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(0.)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(40.0)));
        //System.out.println(response.getInvoices().getFirst().toString());
    }

    @Test
    public void getInvoiceTest() throws IOException {
        System.out.println("@Test - getInvoiceTest");
        UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Invoices response = accountingApi.getInvoice(accessToken,xeroTenantId,invoiceID,4);

        assertThat(response.getInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
        assertThat(response.getInvoices().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 3, 7))));
        assertThat(response.getInvoices().getFirst().getDueDateAsDate(), is(equalTo(LocalDate.of(2019, 3, 13))));
        assertThat(response.getInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getInvoices().getFirst().getInvoiceNumber(), is(equalTo("INV-0006")));
        assertThat(response.getInvoices().getFirst().getReference(), is(equalTo("Tour")));
        assertThat(response.getInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.PAID)));
        assertThat(response.getInvoices().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getSubTotal(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getSubTotal().toString(), is(equalTo("148062.76")));
        assertThat(response.getInvoices().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getTotal(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getTotal().toString(), is(equalTo("148062.76")));
        assertThat(response.getInvoices().getFirst().getInvoiceID(), is(equalTo(UUID.fromString("a03ffcd2-5d91-4c7e-b483-318584e9e439"))));
        assertThat(response.getInvoices().getFirst().getIsDiscounted(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getAmountDue(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getAmountDue().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid().toString(), is(equalTo("148062.76")));
        assertThat(response.getInvoices().getFirst().getAmountPaid(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-07T17:59:28.133Z"))));
        assertThat(response.getInvoices().getFirst().getHasErrors(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getInvoices().getFirst().getContact().getName(), is(equalTo("Liam Gallagher")));
        assertThat(response.getInvoices().getFirst().getContact().getFirstName(), is(equalTo("Liam")));
        assertThat(response.getInvoices().getFirst().getContact().getLastName(), is(equalTo("Gallagher")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getFirstName(), is(equalTo("Debbie")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getLastName(), is(equalTo("Gwyther")));
        assertThat(response.getInvoices().getFirst().getContact().getContactPersons().getFirst().getEmailAddress(), is(equalTo("debbie@rockstar.com")));
        assertThat(response.getInvoices().getFirst().getContact().getContactGroups().getFirst().getName(), is(equalTo("Oasis")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("b18f39d9-7739-4246-9288-72afe939d2d5"))));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getItemCode(), is(equalTo("123")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getInvoices().getFirst().getFullyPaidOnDateAsDate(), is(equalTo(LocalDate.of(2019, 3,19))));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 3,19))));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getAmount(), is(equalTo(148062.76)));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getReference(), is(equalTo("Yahoo")));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getPaymentID(), is(equalTo(UUID.fromString("38928000-e9a0-420c-8884-f624bab2a351"))));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getHasAccount(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getPayments().getFirst().getHasValidationErrors(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("3a2fe7e0-fac7-4ea2-afb2-31cedaabd294"))));
        assertThat(response.getInvoices().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("helo-heros.jpg")));
        assertThat(response.getInvoices().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/a03ffcd2-5d91-4c7e-b483-318584e9e439/Attachments/helo-heros.jpg")));
        assertThat(response.getInvoices().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getInvoices().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/jpeg")));
        assertThat(response.getInvoices().getFirst().getStatusAttributeString(), is(equalTo("ERROR")));
        assertThat(response.getInvoices().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("Invoice # must be unique.")));     
        //System.out.println(response.getInvoices().getFirst().toString());
    }

    @Test
    public void getInvoiceAttachmentsTest() throws IOException {
        System.out.println("@Test - getInvoiceAttachments");
        UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = accountingApi.getInvoiceAttachments(accessToken,xeroTenantId,invoiceID);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("9808ad7f-c8d4-41cf-995e-bc29cb76fd2c"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/4074292c-09b3-456d-84e7-add864c6c39b/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));  
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    

    @Test
    public void getInvoicesTest() throws IOException {
        System.out.println("@Test - getInvoices");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        List<UUID> ids = new ArrayList<>();
        List<String> invoiceNumbers =  new ArrayList<>();
        List<UUID> contactIDs =  new ArrayList<>();
        List<String> statuses =  new ArrayList<>();
        Integer page = null;
        Boolean includeArchived = null;
        Boolean createdByMyApp = null;
        Integer unitdp = null;
        Boolean summaryOnly = null;
        Integer pageSize = null;
        String searchTerm = null;
        Invoices response = accountingApi.getInvoices(accessToken, xeroTenantId, ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, page, includeArchived, createdByMyApp, unitdp, summaryOnly, pageSize, searchTerm);

        assertThat(response.getInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
        assertThat(response.getInvoices().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2018,10,20))));  
        assertThat(response.getInvoices().getFirst().getDueDateAsDate(), is(equalTo(LocalDate.of(2018,12,30))));  
        assertThat(response.getInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getInvoices().getFirst().getInvoiceNumber(), is(equalTo("INV-0001")));
        assertThat(response.getInvoices().getFirst().getReference(), is(equalTo("Red Fish, Blue Fish")));
        assertThat(response.getInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.VOIDED)));
        assertThat(response.getInvoices().getFirst().getSentToContact(), is(equalTo(true)));
        assertThat(response.getInvoices().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getInvoices().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getInvoices().getFirst().getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getInvoices().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getInvoices().getFirst().getInvoiceID(), is(equalTo(UUID.fromString("d4956132-ed94-4dd7-9eaa-aa22dfdf06f2"))));
        assertThat(response.getInvoices().getFirst().getIsDiscounted(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getAmountDue(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getAmountDue().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getAmountCredited().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getAmountCredited(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2018-11-02T16:31:30.160Z"))));
        assertThat(response.getInvoices().getFirst().getHasErrors(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("a3675fc4-f8dd-4f03-ba5b-f1870566bcd7"))));
        assertThat(response.getInvoices().getFirst().getContact().getName(), is(equalTo("Barney Rubble-83203")));
        //System.out.println(response.getInvoices().getFirst().toString());
    }

    @Test
    public void getOnlineInvoiceTest() throws IOException {
        System.out.println("@Test - getOnlineInvoice");
        UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        OnlineInvoices response = accountingApi.getOnlineInvoice(accessToken,xeroTenantId,invoiceID);

        assertThat(response.getOnlineInvoices().getFirst().getOnlineInvoiceUrl(), is(equalTo("https://in.xero.com/bCWCCfytGdTXoJam9HENWlQt07G6zcDaj4gQojHu")));
        //System.out.println(response.getOnlineInvoices().getFirst().toString());
    }

    @Test
    public void updateInvoiceTest() throws IOException {
        System.out.println("@Test - updateInvoice");
        UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Invoices invoices = new Invoices();
        Invoices response = accountingApi.updateInvoice(accessToken,xeroTenantId,invoiceID, invoices,4, null, null);

        assertThat(response.getInvoices().getFirst().getType(), is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
        assertThat(response.getInvoices().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 3,11))));
        assertThat(response.getInvoices().getFirst().getDueDateAsDate(), is(equalTo(LocalDate.of(2019, 3, 12))));
        assertThat(response.getInvoices().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getInvoices().getFirst().getInvoiceNumber(), is(equalTo("INV-0008")));
        assertThat(response.getInvoices().getFirst().getReference(), is(equalTo("My the Force be With You")));
        assertThat(response.getInvoices().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getInvoices().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.SUBMITTED)));
        assertThat(response.getInvoices().getFirst().getSentToContact(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getSubTotal(), is(equalTo(500.0)));
        assertThat(response.getInvoices().getFirst().getSubTotal().toString(), is(equalTo("500.0")));
        assertThat(response.getInvoices().getFirst().getTotalTax(), is(equalTo(75.0)));
        assertThat(response.getInvoices().getFirst().getTotalTax().toString(), is(equalTo("75.0")));
        assertThat(response.getInvoices().getFirst().getTotal(), is(equalTo(575.0)));
        assertThat(response.getInvoices().getFirst().getTotal().toString(), is(equalTo("575.0")));
        assertThat(response.getInvoices().getFirst().getInvoiceID(), is(equalTo(UUID.fromString("4074292c-09b3-456d-84e7-add864c6c39b"))));
        assertThat(response.getInvoices().getFirst().getIsDiscounted(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getAmountDue(), is(equalTo(575.0)));
        assertThat(response.getInvoices().getFirst().getAmountDue().toString(), is(equalTo("575.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        assertThat(response.getInvoices().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getInvoices().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T18:42:08.987Z"))));
        assertThat(response.getInvoices().getFirst().getHasErrors(), is(equalTo(false)));
        assertThat(response.getInvoices().getFirst().getContact().getContactID(), is(equalTo(UUID.fromString("be392c72-c121-4f83-9512-03ac71e54c20"))));
        assertThat(response.getInvoices().getFirst().getContact().getName(), is(equalTo("Luke Skywalker")));        
        assertThat(response.getInvoices().getFirst().getContact().getContactStatus(), is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineItemID(), is(equalTo(UUID.fromString("6de1bf9f-de95-4c47-9287-37305db758c9"))));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Light Saber")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(1.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(500.00)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("200")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(75.0)));
        assertThat(response.getInvoices().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(500.00)));
        //System.out.println(response.getInvoices().getFirst().toString());
    }
    /*
    @Test
    public void updateInvoiceAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateInvoiceAttachmentByFileName");
        UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.updateInvoiceAttachmentByFileName(invoiceID, fileName, body);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("08085449-fda3-45f4-a685-ff44c8a29ee3"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/4074292c-09b3-456d-84e7-add864c6c39b/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));  
        //System.out.println(response.getAttachments().getFirst().toString());
    }
*/
    @Test
    public void getInvoiceRemindersTest() throws IOException {
        System.out.println("@Test - getInvoiceReminders");
        InvoiceReminders response = accountingApi.getInvoiceReminders(accessToken,xeroTenantId);

        assertThat(response.getInvoiceReminders().getFirst().getEnabled(), is(equalTo(false)));
        //System.out.println(response.getInvoiceReminders().getFirst().toString());
    }
}
