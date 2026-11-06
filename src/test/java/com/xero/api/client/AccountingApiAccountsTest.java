package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.accounting.Attachments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiAccountsTest {

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

    @org.junit.jupiter.api.Test
    public void testGetAccounts() throws Exception {
        System.out.println("@Test - getAccounts");


        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Accounts accounts = accountingApi.getAccounts(accessToken, xeroTenantId, ifModifiedSince, where, order);
        assert (accounts.getAccounts().size() == 2);
        assertThat(accounts.getAccounts().getFirst().getCode(), is(equalTo("091")));
        assertThat(accounts.getAccounts().getFirst().getName(), is(equalTo("Business Savings Account")));
        assertThat(accounts.getAccounts().getFirst().getAccountID().toString(), is(equalTo("ebd06280-af70-4bed-97c6-7451a454ad85")));
        assertThat(accounts.getAccounts().getFirst().getType(), is(equalTo(com.xero.models.accounting.AccountType.BANK)));
        assertThat(accounts.getAccounts().getFirst().getBankAccountNumber(), is(equalTo("0209087654321050")));
        assertThat(accounts.getAccounts().getFirst().getBankAccountType(), is(equalTo(com.xero.models.accounting.Account.BankAccountTypeEnum.BANK)));
        assertThat(accounts.getAccounts().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(accounts.getAccounts().getFirst().getTaxType(), is(equalTo("NONE")));
    }

    @Test
    public void testGetAccount() throws Exception {
        System.out.println("@Test - getAccount");

        UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

        Accounts oneAccount = accountingApi.getAccount(accessToken, xeroTenantId, accountID);
        assertThat(oneAccount.getAccounts().getFirst().getName(), is(equalTo("FooBar")));
        assertThat(oneAccount.getAccounts().getFirst().getCode(), is(equalTo("123456")));
    }

    @Test
    public void testCreateAccount() throws Exception {
        System.out.println("@Test - createAccount");
        Account acct = new Account();
        Accounts newAccount = accountingApi.createAccount(accessToken, xeroTenantId, acct, null);
        assertThat(newAccount.getAccounts().getFirst().getName(), is(equalTo("Foobar")));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        System.out.println("@Test - updateAccount");
        UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
        Accounts accts = new Accounts();
        Accounts updatedAccount = accountingApi.updateAccount(accessToken, xeroTenantId, accountID, accts, null);
        assertThat(updatedAccount.getAccounts().getFirst().getName(), is(equalTo("BarFoo")));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        System.out.println("@Test - deleteAccount");
        UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
        Accounts deleteAccount = accountingApi.deleteAccount(accessToken, xeroTenantId, accountID);
        assertThat(deleteAccount.getAccounts().getFirst().getStatus().toString(), is(equalTo("DELETED")));
    }

    @Test
    public void testGetAccountAttachments() throws Exception {
        System.out.println("@Test - getAccountAttachments");
        UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
        Attachments accountsAttachments = accountingApi.getAccountAttachments(accessToken, xeroTenantId, accountID);
        assertThat(accountsAttachments.getAttachments().getFirst().getAttachmentID().toString(), is(equalTo("52a643be-cd5c-489f-9778-53a9fd337756")));
        assertThat(accountsAttachments.getAttachments().getFirst().getFileName().toString(), is(equalTo("sample5.jpg")));
        assertThat(accountsAttachments.getAttachments().getFirst().getMimeType().toString(), is(equalTo("image/jpg")));
        assertThat(accountsAttachments.getAttachments().getFirst().getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));


    }

    @Disabled("Enable when OpenAPI schema is changed to binary wherever octet/stream is used")
    @Test
    public void testCreateAccountAttachmentByFileName() throws Exception {

        System.out.println("@Test - createAccountAttachmentByFileName");
        UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

        ClassLoader classLoader = getClass().getClassLoader();
        File bytes = new File(classLoader.getResource("helo-heros.jpg").getFile());

        String newFileName = "sample5.jpg";
        Attachments createAccountsAttachments = accountingApi.createAccountAttachmentByFileName(accessToken, xeroTenantId, accountID, newFileName, bytes, null);

        assertThat(createAccountsAttachments.getAttachments().getFirst().getAttachmentID().toString(), is(equalTo("ab95b276-9dce-4925-9077-439818ba270f")));
        assertThat(createAccountsAttachments.getAttachments().getFirst().getFileName().toString(), is(equalTo("sample5.jpg")));
        assertThat(createAccountsAttachments.getAttachments().getFirst().getMimeType().toString(), is(equalTo("image/jpg")));
        assertThat(createAccountsAttachments.getAttachments().getFirst().getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));

    }
/*
	@Test
	public void testUpdateAccountAttachmentByFileName() throws Exception {		
		System.out.println("@Test - updateAccountAttachmentByFileName");
		
		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
		ClassLoader classLoader = getClass().getClassLoader();
		File bytes = new File(classLoader.getResource("helo-heros.jpg").getFile());
    	
    	String newFileName = "sample.jpg";
		Attachments createAccountsAttachments = accountingApi.updateAccountAttachmentByFileName(accountID, newFileName, bytes);					
		assertThat(createAccountsAttachments.getAttachments().getFirst().getAttachmentID().toString(), is(equalTo("3fa85f64-5717-4562-b3fc-2c963f66afa6")));
		assertThat(createAccountsAttachments.getAttachments().getFirst().getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(createAccountsAttachments.getAttachments().getFirst().getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(createAccountsAttachments.getAttachments().getFirst().getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
	}
	*/
}
