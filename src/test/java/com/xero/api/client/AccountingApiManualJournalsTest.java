package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.ManualJournals;
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

public class AccountingApiManualJournalsTest {

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
    public void createManualJournalsTest() throws IOException {
        System.out.println("@Test - createManualJournals");
        ManualJournals manualJournals = new ManualJournals();
        ManualJournals response = accountingApi.createManualJournals(accessToken,xeroTenantId,manualJournals, false, null);

        assertThat(response.getManualJournals().getFirst().getNarration(), is(equalTo("Foo bar")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount(), is(equalTo(100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount().toString(), is(equalTo("100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getAccountCode(), is(equalTo("400")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getDescription(), is(equalTo("Hello there")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount(), is(equalTo(-100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount().toString(), is(equalTo("-100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getAccountCode(), is(equalTo("400")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getDescription(), is(equalTo("Goodbye")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTaxType(), is(equalTo("NONE"))); 
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTracking().getFirst().getTrackingCategoryID(), is(equalTo(UUID.fromString("6a68adde-f210-4465-b0a9-0d8cc6f50762"))));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTracking().getFirst().getTrackingOptionID(), is(equalTo(UUID.fromString("dc54c220-0140-495a-b925-3246adc0075f"))));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTracking().getFirst().getName(), is(equalTo("Simpsons")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTracking().getFirst().getOption(), is(equalTo("Bart")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,14))));  
        assertThat(response.getManualJournals().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getManualJournals().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.DRAFT)));
        assertThat(response.getManualJournals().getFirst().getShowOnCashBasisReports(), is(equalTo(true)));
        assertThat(response.getManualJournals().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-14T20:39:32.920Z"))));
        assertThat(response.getManualJournals().getFirst().getManualJournalID(), is(equalTo(UUID.fromString("d312dd5e-a53e-46d1-9d51-c569ef4570b7"))));
        assertThat(response.getManualJournals().getFirst().getWarnings().getFirst().getMessage(), is(equalTo("Account code ''476'' has been removed as it does not match a recognised account.")));
        assertThat(response.getManualJournals().getFirst().getValidationErrors().getFirst().getMessage(), is(equalTo("The total debits (100.00) must equal total credits (-10.00)")));
        //System.out.println(response.getManualJournals().getFirst().toString());
    }
    
 
    @Disabled("Enable when OpenAPI schema is changed to binary wherever octet/stream is used")
    @Test
    public void createManualJournalAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createManualJournalAttachmentByFileName");
        UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.createManualJournalAttachmentByFileName(accessToken,xeroTenantId,manualJournalID, fileName, body, null);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("47ac97ff-d4f9-48a0-8a8e-49fae29129e7"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }

    @Test
    public void getManualJournalTest() throws IOException {
        System.out.println("@Test - getManualJournal");
        UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        ManualJournals response = accountingApi.getManualJournal(accessToken,xeroTenantId,manualJournalID);

        assertThat(response.getManualJournals().getFirst().getNarration(), is(equalTo("These aren''t the droids you are looking for")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount(), is(equalTo(100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount().toString(), is(equalTo("100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getAccountCode(), is(equalTo("429")));
        assertThat(response.getManualJournals().getFirst().getNarration(), is(equalTo("These aren''t the droids you are looking for")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount(), is(equalTo(-100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount().toString(), is(equalTo("-100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getAccountCode(), is(equalTo("200")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getDescription(), is(equalTo("Yes the are")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTaxType(), is(equalTo("NONE"))); 
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getManualJournals().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getManualJournals().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.POSTED)));
        assertThat(response.getManualJournals().getFirst().getShowOnCashBasisReports(), is(equalTo(true)));
        assertThat(response.getManualJournals().getFirst().getHasAttachments(), is(equalTo(true)));
        assertThat(response.getManualJournals().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-12T02:19:48.147Z"))));
        assertThat(response.getManualJournals().getFirst().getManualJournalID(), is(equalTo(UUID.fromString("99cb8353-ce73-4a5d-8e0d-8b0edf86cfc4"))));        
        assertThat(response.getManualJournals().getFirst().getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("166ca8f8-8bc6-4780-8466-a0e474d586ea"))));
        assertThat(response.getManualJournals().getFirst().getAttachments().getFirst().getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getManualJournals().getFirst().getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/manualjournal/99cb8353-ce73-4a5d-8e0d-8b0edf86cfc4/Attachments/giphy.gif")));
        assertThat(response.getManualJournals().getFirst().getAttachments().getFirst().getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getManualJournals().getFirst().getAttachments().getFirst().getContentLength(), is(equalTo(495727)));
        //System.out.println(response.getManualJournals().getFirst().toString());
    }

    @Test
    public void getManualJournalAttachmentsTest() throws IOException {
        System.out.println("@Test - getManualJournalAttachments");
        UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = accountingApi.getManualJournalAttachments(accessToken,xeroTenantId,manualJournalID);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("16e86f32-3e25-4209-8662-c0dfd91b654c"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(2878711)));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }

    @Test
    public void getManualJournalsTest() throws IOException {
        System.out.println("@Test - getManualJournals");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer page = null;
        Integer pageSize = null;
        ManualJournals response = accountingApi.getManualJournals(accessToken,xeroTenantId,ifModifiedSince, where, order, page, pageSize);

        assertThat(response.getManualJournals().getFirst().getNarration(), is(equalTo("Reversal: These aren''t the droids you are looking for")));
        assertThat(response.getManualJournals().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,21))));  
        assertThat(response.getManualJournals().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getManualJournals().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.POSTED)));
        assertThat(response.getManualJournals().getFirst().getShowOnCashBasisReports(), is(equalTo(true)));
        assertThat(response.getManualJournals().getFirst().getHasAttachments(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-12T02:19:48.083Z"))));
        assertThat(response.getManualJournals().getFirst().getManualJournalID(), is(equalTo(UUID.fromString("0b159335-606b-485f-b51b-97b3b32bad32"))));
        //System.out.println(response.getManualJournals().getFirst().toString());
    }

    @Test
    public void updateManualJournalTest() throws IOException {
        System.out.println("@Test - updateManualJournal");
        UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        ManualJournals manualJournals = new ManualJournals();
        ManualJournals response = accountingApi.updateManualJournal(accessToken,xeroTenantId,manualJournalID, manualJournals, null);

        assertThat(response.getManualJournals().getFirst().getNarration(), is(equalTo("Hello Xero")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount(), is(equalTo(100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getLineAmount().toString(), is(equalTo("100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getAccountCode(), is(equalTo("400")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getDescription(), is(equalTo("Hello there")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getTaxType(), is(equalTo("NONE")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().getFirst().getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount(), is(equalTo(-100.0)));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getLineAmount().toString(), is(equalTo("-100.0")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getAccountCode(), is(equalTo("400")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getDescription(), is(equalTo("Goodbye")));
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getTaxType(), is(equalTo("NONE"))); 
        assertThat(response.getManualJournals().getFirst().getHasAttachments(), is(equalTo(false)));          
        assertThat(response.getManualJournals().getFirst().getJournalLines().get(1).getIsBlank(), is(equalTo(false)));
        assertThat(response.getManualJournals().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        assertThat(response.getManualJournals().getFirst().getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getManualJournals().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.DRAFT)));
        assertThat(response.getManualJournals().getFirst().getShowOnCashBasisReports(), is(equalTo(true)));
        assertThat(response.getManualJournals().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-12T02:28:56.820Z"))));
        assertThat(response.getManualJournals().getFirst().getManualJournalID(), is(equalTo(UUID.fromString("07eac261-78ef-47a0-a0eb-a57b74137877"))));
        //System.out.println(response.getManualJournals().getFirst().toString());
    }
    /*
    @Test
    public void updateManualJournalAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateManualJournalAttachmentByFileName");
        UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        Attachments response = accountingApi.updateManualJournalAttachmentByFileName(manualJournalID, fileName, body);

        assertThat(response.getAttachments().getFirst().getAttachmentID(), is(equalTo(UUID.fromString("16e86f32-3e25-4209-8662-c0dfd91b654c"))));
        assertThat(response.getAttachments().getFirst().getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().getFirst().getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().getFirst().getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().getFirst().getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().getFirst().toString());
    }
    */
}
