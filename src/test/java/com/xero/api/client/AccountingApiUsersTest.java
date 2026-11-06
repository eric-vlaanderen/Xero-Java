package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiUsersTest {

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
            System.out.println("Sleep for 30 seconds");
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
    public void getUserTest() throws IOException {
        System.out.println("@Test - getUser");
        UUID userID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Users response = accountingApi.getUser(accessToken,xeroTenantId,userID);

        assertThat(response.getUsers().getFirst().getUserID(), is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
        assertThat(response.getUsers().getFirst().getEmailAddress(), is(equalTo("test@email.com")));
        assertThat(response.getUsers().getFirst().getFirstName(), is(equalTo("Test")));
        assertThat(response.getUsers().getFirst().getLastName(), is(equalTo("Xero")));
        assertThat(response.getUsers().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-10-20T18:14:21.613Z"))));
        assertThat(response.getUsers().getFirst().getIsSubscriber(), is(equalTo(false)));
        assertThat(response.getUsers().getFirst().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        //System.out.println(response.getUsers().getFirst().toString());
    }
    
    @Test
    public void getUsersTest() throws IOException {
        System.out.println("@Test - getUsers");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Users response = accountingApi.getUsers(accessToken,xeroTenantId,ifModifiedSince, where, order);

        assertThat(response.getUsers().getFirst().getUserID(), is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
        assertThat(response.getUsers().getFirst().getEmailAddress(), is(equalTo("test@email.com")));
        assertThat(response.getUsers().getFirst().getFirstName(), is(equalTo("Test")));
        assertThat(response.getUsers().getFirst().getLastName(), is(equalTo("Xero")));
        assertThat(response.getUsers().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2017-10-20T18:14:21.613Z"))));
        assertThat(response.getUsers().getFirst().getIsSubscriber(), is(equalTo(false)));
        assertThat(response.getUsers().getFirst().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        //System.out.println(response.getUsers().toString());
    }
}
