package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.bankfeeds.Statements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BankfeedApiStatementTest {

    private static boolean setUpIsDone = false;
    ApiClient defaultClient;
    BankFeedsApi bankfeedsApi;
    String accessToken;
    String xeroTenantId;

    @BeforeEach
    public void setUp() {

        // Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";

        defaultClient = new ApiClient(ConfigurationLoader.getProperty("bankfeeds.api.url"), null, null, null, null);
        bankfeedsApi = BankFeedsApi.getInstance(defaultClient);

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
        bankfeedsApi = null;
        defaultClient = null;
    }

    @Test
    public void testCreateStatements() throws Exception {
        System.out.println("@Test - createStatements");

        Statements newStatements = new Statements();
        Statements response = bankfeedsApi.createStatements(accessToken, xeroTenantId, newStatements, null);
        assertThat(response.getItems().getFirst().getId().toString(), (equalTo("d69b02b7-a30c-464a-99cf-ba9770373c61")));
        assertThat(response.getItems().getFirst().getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
        assertThat(response.getItems().getFirst().getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.PENDING)));
    }

    @Test
    public void testGetStatement() throws Exception {
        System.out.println("@Test - getStatement");

        Statements newStatements = new Statements();
        Statements response = bankfeedsApi.createStatements(accessToken, xeroTenantId, newStatements, null);
        assertThat(response.getItems().getFirst().getId().toString(), (equalTo("d69b02b7-a30c-464a-99cf-ba9770373c61")));
        assertThat(response.getItems().getFirst().getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
        assertThat(response.getItems().getFirst().getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.PENDING)));
    }

    @Test
    public void testGetStatements() throws Exception {
        System.out.println("@Test - getStatements");
        Statements response = bankfeedsApi.getStatements(accessToken, xeroTenantId, 1, 3, null, null);
        assertThat(response.getItems().getFirst().getId().toString(), (equalTo("9817e4b8-82b3-4526-91f7-040bd278053f")));
        assertThat(response.getItems().getFirst().getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
        assertThat(response.getItems().getFirst().getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.REJECTED)));
        assertThat(response.getItems().getFirst().getStartDate(), is(equalTo(LocalDate.of(2019, 8, 01))));
        assertThat(response.getItems().getFirst().getEndDate(), is(equalTo(LocalDate.of(2019, 8, 15))));
        assertThat(response.getItems().getFirst().getStartBalance().getAmount().toString(), is(equalTo("100.0")));
        assertThat(response.getItems().getFirst().getStartBalance().getCreditDebitIndicator(), is(equalTo(com.xero.models.bankfeeds.CreditDebitIndicator.CREDIT)));
        assertThat(response.getItems().getFirst().getEndBalance().getAmount().toString(), is(equalTo("150.0")));
        assertThat(response.getItems().getFirst().getEndBalance().getCreditDebitIndicator(), is(equalTo(com.xero.models.bankfeeds.CreditDebitIndicator.CREDIT)));
        assertThat(response.getItems().getFirst().getErrors().getFirst().getTitle(), is(equalTo("Duplicate Statement Received")));
        assertThat(response.getItems().getFirst().getErrors().getFirst().getStatus().toString(), is(equalTo("409")));
        assertThat(response.getItems().getFirst().getErrors().getFirst().getDetail(), is(equalTo("The received statement was marked as a duplicate.")));
        assertThat(response.getItems().getFirst().getErrors().getFirst().getType(), is(equalTo(com.xero.models.bankfeeds.Error.TypeEnum.DUPLICATE_STATEMENT)));
    }
}
