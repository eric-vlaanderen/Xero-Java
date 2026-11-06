package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.finance.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FinanceApiTest {

    private static boolean setUpIsDone = false;
    ApiClient defaultClient;
    FinanceApi financeApi;
    String accessToken;
    String xeroTenantId;

    @BeforeEach
    public void setUp() {
        // Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("finance.api.url"), null, null, null, null);
        financeApi = FinanceApi.getInstance(defaultClient);

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
        financeApi = null;
        defaultClient = null;
    }

    @Test
    public void testGetCashValidation() throws Exception {
        System.out.println("@Test - getCashValidation");
        List<CashValidationResponse> response = new ArrayList<CashValidationResponse>();

        try {
            //UUID subscriptionId = UUID.fromString("73151de8-3676-4887-a021-edec960dd537");
            response = financeApi.getCashValidation(accessToken, "73151de8-3676-4887-a021-edec960dd537", null, null, null);
            assertThat(response.getFirst().getAccountId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.getFirst().getStatementBalance().getValue().toString(), (equalTo("100.0")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementBalanceSheet() throws Exception {
        System.out.println("@Test - getFinancialStatementBalanceSheet");

        try {
            BalanceSheetResponse response = financeApi.getFinancialStatementBalanceSheet(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-06-30");
            assertThat(response.getAsset().getAccountTypes().getFirst().getAccounts().getFirst().getAccountID().toString(), (equalTo("abcdeabc-80bb-47f5-9418-d1fc2241b838")));
            assertThat(response.getBalanceDate().toString(), (equalTo("2021-05-12")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementCashflow() throws Exception {
        System.out.println("@Test - getFinancialStatementCashflow");

        try {
            CashflowResponse response = financeApi.getFinancialStatementCashflow(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15", "2020-09-15");
            assertThat(response.getCashflowActivities().getFirst().getCashflowTypes().getFirst().getAccounts().getFirst().getAccountId().toString(), (equalTo("abcdefab-4d1e-4d1a-9e4c-68b2c2a278e2")));
            assertThat(response.getStartDate().toString(), (equalTo("2018-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementProfitAndLoss() throws Exception {
        System.out.println("@Test - getFinancialStatementProfitAndLoss");

        try {
            ProfitAndLossResponse response = financeApi.getFinancialStatementProfitAndLoss(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15", "2020-09-15");
            assertThat(response.getRevenue().getAccountTypes().getFirst().getAccounts().getFirst().getAccountID().toString(), (equalTo("abcdefab-2006-43c2-a5da-3c0e5f43b452")));
            assertThat(response.getStartDate().toString(), (equalTo("2020-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementTrialBalance() throws Exception {
        System.out.println("@Test - getFinancialStatementTrialBalance");

        try {
            TrialBalanceResponse response = financeApi.getFinancialStatementTrialBalance(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15");
            assertThat(response.getAccounts().getFirst().getAccountId().toString(), (equalTo("abcdefab-3bbf-4f2a-9e4c-20ec7b8e6b41")));
            assertThat(response.getStartDate().toString(), (equalTo("2020-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementContactsRevenue() throws Exception {
        System.out.println("@Test - getFinancialStatementContactsRevenue");
        List<UUID> contacts = new ArrayList<UUID>();
        contacts.add(UUID.fromString("1f580fe2-0659-31ee-eeb4-5c49d15d8bfa"));
        try {
            IncomeByContactResponse response = financeApi.getFinancialStatementContactsRevenue(accessToken, "73151de8-3676-4887-a021-edec960dd537", contacts, true, null, null);
            assertThat(response.getContacts().getFirst().getName(), (equalTo("FirstContact")));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetFinancialStatementContactsExpense() throws Exception {
        System.out.println("@Test - getFinancialStatementContactsExpense");
        List<UUID> contacts = new ArrayList<UUID>();
        contacts.add(UUID.fromString("1f580fe2-0659-31ee-eeb4-5c49d15d8bfa"));
        try {
            IncomeByContactResponse response = financeApi.getFinancialStatementContactsExpense(accessToken, "73151de8-3676-4887-a021-edec960dd537", contacts, true, null, null);
            assertThat(response.getContacts().getFirst().getName(), (equalTo("FirstContact")));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
    public void testGetBankStatementAccounting() throws Exception {
        System.out.println("@Test - getBankStatementAccounting");
        UUID bankAccountID = UUID.fromString("1234eee9-47f0-4179-bd46-9adb4f21cc7f");
        try {
            BankStatementAccountingResponse response = financeApi.getBankStatementAccounting(accessToken, "73151de8-3676-4887-a021-edec960dd537", bankAccountID, "2021-01-01", "2021-01-02", null);
            assertThat(response.getStatements().getFirst().getStatementId().toString(), (equalTo("7c29eee9-47f0-4179-bd46-9adb4f21cc7f")));
            assertThat(response.getStatements().getFirst().getStatementLines().getFirst().getPayments().getFirst().getInvoice().getContact().getContactName(), (equalTo("Bob")));
            assertThat(response.getStatements().getFirst().getEndBalance(), (equalTo(200.0)));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }
}
