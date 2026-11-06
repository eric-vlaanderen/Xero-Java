package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.accounting.Organisations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AccountingApiOrganisationsTest {

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
    public void getOrganisationsTest() throws IOException {
        System.out.println("@Test - getOrganisations");
        Organisations response = accountingApi.getOrganisations(accessToken,xeroTenantId);

        assertThat(response.getOrganisations().getFirst().getOrganisationID(), is(equalTo(UUID.fromString("b2c885a9-4bb9-4a00-9b6e-6c2bf60b1a2b"))));
        assertThat(response.getOrganisations().getFirst().getApIKey(), is(equalTo("CTJ60UH519MXQIXEJSDPDALS3EOZ5Y")));
        assertThat(response.getOrganisations().getFirst().getName(), is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
        assertThat(response.getOrganisations().getFirst().getLegalName(), is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
        assertThat(response.getOrganisations().getFirst().getPaysTax(), is(equalTo(true)));
        assertThat(response.getOrganisations().getFirst().getVersion(), is(equalTo(com.xero.models.accounting.Organisation.VersionEnum.NZ)));
        assertThat(response.getOrganisations().getFirst().getOrganisationType(), is(equalTo(com.xero.models.accounting.Organisation.OrganisationTypeEnum.COMPANY)));
        assertThat(response.getOrganisations().getFirst().getBaseCurrency(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOrganisations().getFirst().getCountryCode(), is(equalTo(com.xero.models.accounting.CountryCode.NZ)));
        assertThat(response.getOrganisations().getFirst().getIsDemoCompany(), is(equalTo(false)));
        assertThat(response.getOrganisations().getFirst().getOrganisationStatus(), is(equalTo("ACTIVE")));
        assertThat(response.getOrganisations().getFirst().getFinancialYearEndDay(), is(equalTo(31)));
        assertThat(response.getOrganisations().getFirst().getFinancialYearEndMonth(), is(equalTo(3)));
        assertThat(response.getOrganisations().getFirst().getTaxNumber(), is(equalTo("071-138-054")));
        assertThat(response.getOrganisations().getFirst().getSalesTaxBasis(), is(equalTo(com.xero.models.accounting.Organisation.SalesTaxBasisEnum.PAYMENTS)));
        assertThat(response.getOrganisations().getFirst().getSalesTaxPeriod(), is(equalTo(com.xero.models.accounting.Organisation.SalesTaxPeriodEnum.TWOMONTHS)));
        assertThat(response.getOrganisations().getFirst().getDefaultSalesTax(), is(equalTo("Tax Exclusive")));
        assertThat(response.getOrganisations().getFirst().getDefaultPurchasesTax(), is(equalTo("Tax Exclusive")));
        assertThat(response.getOrganisations().getFirst().getPeriodLockDateAsDate(), is(equalTo(LocalDate.of(2018,12,31))));  
        assertThat(response.getOrganisations().getFirst().getEndOfYearLockDateAsDate(), is(equalTo(LocalDate.of(2018,12,31))));  
        assertThat(response.getOrganisations().getFirst().getCreatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2016-02-18T20:29:53Z"))));  
        assertThat(response.getOrganisations().getFirst().getTimezone(), is(equalTo(com.xero.models.accounting.TimeZone.NEWZEALANDSTANDARDTIME)));
        assertThat(response.getOrganisations().getFirst().getOrganisationEntityType(), is(equalTo(com.xero.models.accounting.Organisation.OrganisationEntityTypeEnum.COMPANY)));
        assertThat(response.getOrganisations().getFirst().getShortCode(), is(equalTo("!mBdtL")));
        assertThat(response.getOrganisations().getFirst().getPropertyClass(), is(equalTo(com.xero.models.accounting.Organisation.PropertyClassEnum.PREMIUM)));
        assertThat(response.getOrganisations().getFirst().getEdition(), is(equalTo(com.xero.models.accounting.Organisation.EditionEnum.BUSINESS)));
        //assertThat(response.getOrganisations().getFirst().getRegistrationNumber(), is(equalTo("STRING_ONLY_PLACEHOLDER")));        
        //assertThat(response.getOrganisations().getFirst().getLineOfBusiness(), is(equalTo("STRING_ONLY_PLACEHOLDER")));
        //assertThat(response.getOrganisations().getFirst().getAddresses(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().getFirst().getPhones(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().getFirst().getExternalLinks(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().getFirst().getPaymentTerms(), is(equalTo("MODEL_PLACEHOLDER")));
        //System.out.println(response.getOrganisations().getFirst().toString());
    }
}
