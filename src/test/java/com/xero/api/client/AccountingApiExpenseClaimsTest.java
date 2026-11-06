package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.ExpenseClaims;
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

public class AccountingApiExpenseClaimsTest {

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
    public void createExpenseClaimTest() throws IOException {
        System.out.println("@Test - createExpenseClaim");
        ExpenseClaims expenseClaims = new ExpenseClaims();
        ExpenseClaims response = accountingApi.createExpenseClaims(accessToken, xeroTenantId, expenseClaims, null);

        assertThat(response.getExpenseClaims().getFirst().getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.SUBMITTED)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:30.107Z"))));
        assertThat(response.getExpenseClaims().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        //assertThat(response.getExpenseClaims().getFirst().getPayments(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getExpenseClaims().getFirst().getPaymentDueDate(), is(equalTo(LocalDate.of(YYYY, MM, DD))));  
        //assertThat(response.getExpenseClaims().getFirst().getReportingDate(), is(equalTo(LocalDate.of(YYYY, MM, DD))));  
        //assertThat(response.getExpenseClaims().getFirst().getReceiptID(), is(equalTo(UUID.fromString("UUID_PLACEHOLDER"))));
        //System.out.println(response.getExpenseClaims().getFirst().toString());
    }


    @Test
    public void getExpenseClaimTest() throws IOException {
        System.out.println("@Test - getExpenseClaim");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        ExpenseClaims response = accountingApi.getExpenseClaim(accessToken, xeroTenantId, expenseClaimID);

        assertThat(response.getExpenseClaims().getFirst().getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
        assertThat(response.getExpenseClaims().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().getFirst().toString());
    }

    @Test
    public void getExpenseClaimsTest() throws IOException {
        System.out.println("@Test - getExpenseClaims");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        ExpenseClaims response = accountingApi.getExpenseClaims(accessToken, xeroTenantId, ifModifiedSince, where, order);

        // TODO: test validations
        assertThat(response.getExpenseClaims().getFirst().getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
        assertThat(response.getExpenseClaims().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().getFirst().toString());
    }

    @Test
    public void updateExpenseClaimTest() throws IOException {
        System.out.println("@Test - updateExpenseClaim");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
        ExpenseClaims expenseClaims = new ExpenseClaims();
        ExpenseClaims response = accountingApi.updateExpenseClaim(accessToken, xeroTenantId, expenseClaimID, expenseClaims, null);

        // TODO: test validations
        assertThat(response.getExpenseClaims().getFirst().getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().getFirst().getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().getFirst().getUser().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
        assertThat(response.getExpenseClaims().getFirst().getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().getFirst().getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
        assertThat(response.getExpenseClaims().getFirst().getReceipts().getFirst().getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
        assertThat(response.getExpenseClaims().getFirst().getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().getFirst().getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().getFirst().toString());
    }
}
