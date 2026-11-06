package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Allocations;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.CreditNotes;
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

public class AccountingApiCreditNotesTest {

    private static boolean setUpIsDone = false;
    ApiClient defaultClient;
    AccountingApi accountingApi;
    String accessToken;
    String xeroTenantId;
    File body;

    @BeforeEach
    public void setUp() {
        // Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";

        defaultClient = new ApiClient(ConfigurationLoader.getProperty("accounting.api.url"), null, null, null, null);
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
    public void createCreditNoteTest() throws IOException {
        System.out.println("@Test - createCreditNote");
        Boolean summarizeErrors = false;
        Integer unitdp = null;
        CreditNotes creditNotes = new CreditNotes();
        CreditNotes response = accountingApi.createCreditNotes(accessToken, xeroTenantId, creditNotes, summarizeErrors, unitdp, null);

        assertThat(response.getCreditNotes().getFirst().getType(), is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT)));
        assertThat(response.getCreditNotes().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.DRAFT)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getCreditNotes().getFirst().getTotalTax(), is(equalTo(6.0)));
        assertThat(response.getCreditNotes().getFirst().getTotalTax().toString(), is(equalTo("6.0")));
        assertThat(response.getCreditNotes().getFirst().getTotal(), is(equalTo(46.0)));
        assertThat(response.getCreditNotes().getFirst().getTotal().toString(), is(equalTo("46.0")));
        assertThat(response.getCreditNotes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-05T19:05:02.650Z"))));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteID(), is(equalTo(UUID.fromString("f9256f04-5a99-4680-acb9-6b4639cc439a"))));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit(), is(equalTo(46.0)));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit().toString(), is(equalTo("46.0")));
        assertThat(response.getCreditNotes().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("An existing Credit Note with the specified CreditNoteID could not be found")));
        assertThat(response.getCreditNotes().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getCreditNotes().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Foobar")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(2.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("400")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(6.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(40.0)));
        //System.out.println(response.getCreditNotes().getFirst().toString());
    }

    @Test
    public void createCreditNoteAllocationTest() throws IOException {
        System.out.println("@Test - createCreditNoteAllocation");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        Allocations allocations = new Allocations();
        Allocations response = accountingApi.createCreditNoteAllocation(accessToken, xeroTenantId, creditNoteID, allocations, false, null);

        assertThat(response.getAllocations().getFirst().getAmount(), is(equalTo(1.0)));
        assertThat(response.getAllocations().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
        //System.out.println(response.getAllocations().getFirst().toString());
    }

    /*
    @Test
    public void createCreditNoteAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createCreditNoteAttachmentByFileName");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.createCreditNoteAttachmentByFileName(accessToken,xeroTenantId,creditNoteID, fileName, false, body);
        
        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("91bbae3f-5de5-4e3d-875f-8662f25897bd"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("sample5.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/sample5.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
*/
    @Test
    public void getCreditNoteTest() throws IOException {
        System.out.println("@Test - getCreditNote");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        Integer unitdp = null;
        CreditNotes response = accountingApi.getCreditNote(accessToken, xeroTenantId, creditNoteID, unitdp);

        assertThat(response.getCreditNotes().getFirst().getType(), is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCRECCREDIT)));
        assertThat(response.getCreditNotes().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
        assertThat(response.getCreditNotes().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
        assertThat(response.getCreditNotes().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal(), is(equalTo(30.0)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal().toString(), is(equalTo("30.0")));
        assertThat(response.getCreditNotes().getFirst().getTotalTax(), is(equalTo(4.5)));
        assertThat(response.getCreditNotes().getFirst().getTotalTax().toString(), is(equalTo("4.5")));
        assertThat(response.getCreditNotes().getFirst().getTotal(), is(equalTo(34.5)));
        assertThat(response.getCreditNotes().getFirst().getTotal().toString(), is(equalTo("34.5")));
        assertThat(response.getCreditNotes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-05T18:59:06.157Z"))));
        assertThat(response.getCreditNotes().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteID(), is(equalTo(UUID.fromString("249f15fa-f2a7-4acc-8769-0984103f2225"))));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteNumber(), is(equalTo("CN-0005")));
        assertThat(response.getCreditNotes().getFirst().getReference(), is(equalTo("US Tour")));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit(), is(equalTo(32.5)));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit().toString(), is(equalTo("32.5")));
        assertThat(response.getCreditNotes().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getReference(), is(equalTo("Too much")));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 14))));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getAmount(), is(equalTo(2.0)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getPaymentID(), is(equalTo(UUID.fromString("6b037c9b-2e5d-4905-84d3-eabfb3438242"))));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getHasAccount(), is(equalTo(false)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getHasValidationErrors(), is(equalTo(false)));
        //System.out.println(response.getCreditNotes().getFirst().toString());
    }

    @Test
    public void getCreditNoteAttachmentsTest() throws IOException {
        System.out.println("@Test - getCreditNoteAttachments");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        Attachments response = accountingApi.getCreditNoteAttachments(accessToken, xeroTenantId, creditNoteID);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("b7eb1fc9-a0f9-4e8e-9373-6689f5350832"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.png")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/png")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/HelloWorld.png")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(76091)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }

    @Test
    public void getCreditNotesTest() throws IOException {
        System.out.println("@Test - getCreditNotes");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer page = null;
        Integer unitdp = null;
        Integer pageSize = null;
        CreditNotes response = accountingApi.getCreditNotes(accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp, pageSize);

        assertThat(response.getCreditNotes().getFirst().getType(), is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCRECCREDIT)));
        assertThat(response.getCreditNotes().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
        assertThat(response.getCreditNotes().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
        assertThat(response.getCreditNotes().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal(), is(equalTo(30.0)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal().toString(), is(equalTo("30.0")));
        assertThat(response.getCreditNotes().getFirst().getTotalTax(), is(equalTo(4.5)));
        assertThat(response.getCreditNotes().getFirst().getTotalTax().toString(), is(equalTo("4.5")));
        assertThat(response.getCreditNotes().getFirst().getTotal(), is(equalTo(34.5)));
        assertThat(response.getCreditNotes().getFirst().getTotal().toString(), is(equalTo("34.5")));
        assertThat(response.getCreditNotes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-05T18:59:06.157Z"))));
        assertThat(response.getCreditNotes().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteID(), is(equalTo(UUID.fromString("249f15fa-f2a7-4acc-8769-0984103f2225"))));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteNumber(), is(equalTo("CN-0005")));
        assertThat(response.getCreditNotes().getFirst().getReference(), is(equalTo("US Tour")));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit(), is(equalTo(32.5)));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit().toString(), is(equalTo("32.5")));
        assertThat(response.getCreditNotes().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getReference(), is(equalTo("Too much")));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 14))));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getAmount(), is(equalTo(2.0)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getPaymentID(), is(equalTo(UUID.fromString("6b037c9b-2e5d-4905-84d3-eabfb3438242"))));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getHasAccount(), is(equalTo(false)));
        assertThat(response.getCreditNotes().getFirst().getPayments().getFirst().getHasValidationErrors(), is(equalTo(false)));
        //System.out.println(response.getHistoryRecords().getFirst().toString());
    }

    @Test
    public void updateCreditNoteTest() throws IOException {
        System.out.println("@Test - updateCreditNote");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        Integer unitdp = null;
        CreditNotes creditNotes = new CreditNotes();
        CreditNotes response = accountingApi.updateCreditNote(accessToken, xeroTenantId, creditNoteID, creditNotes, unitdp, null);

        assertThat(response.getCreditNotes().getFirst().getType(), is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT)));
        assertThat(response.getCreditNotes().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 01, 05))));
        assertThat(response.getCreditNotes().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
        assertThat(response.getCreditNotes().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getDescription(), is(equalTo("Foobar")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getQuantity(), is(equalTo(2.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getAccountCode(), is(equalTo("400")));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getTaxAmount(), is(equalTo(6.0)));
        assertThat(response.getCreditNotes().getFirst().getLineItems().getFirst().getLineAmount(), is(equalTo(40.0)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getCreditNotes().getFirst().getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getCreditNotes().getFirst().getTotalTax(), is(equalTo(6.0)));
        assertThat(response.getCreditNotes().getFirst().getTotalTax().toString(), is(equalTo("6.0")));
        assertThat(response.getCreditNotes().getFirst().getTotal(), is(equalTo(46.0)));
        assertThat(response.getCreditNotes().getFirst().getTotal().toString(), is(equalTo("46.0")));
        assertThat(response.getCreditNotes().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-05T19:05:04.223Z"))));
        assertThat(response.getCreditNotes().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getCreditNotes().getFirst().getCreditNoteID(), is(equalTo(UUID.fromString("f9256f04-5a99-4680-acb9-6b4639cc439a"))));
        assertThat(response.getCreditNotes().getFirst().getReference(), is(equalTo("HelloWorld")));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getCreditNotes().getFirst().getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit(), is(equalTo(46.0)));
        assertThat(response.getCreditNotes().getFirst().getRemainingCredit().toString(), is(equalTo("46.0")));
        //System.out.println(response.getCreditNotes().getFirst().toString());
    }
    /*
    @Test
    public void updateCreditNoteAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateCreditNoteAttachmentByFileName");
        UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
      
        Attachments response = accountingApi.updateCreditNoteAttachmentByFileName(creditNoteID, fileName, body);
        
        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("103e49f1-e47c-4b4d-b5e8-77d9d00fa70a"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));  
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    */
}
