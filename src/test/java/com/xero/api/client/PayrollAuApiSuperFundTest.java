package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.SuperFund;
import com.xero.models.payrollau.SuperFunds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollAuApiSuperFundTest {

	ApiClient defaultClient; 
    PayrollAuApi payrollAuApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrollau.api.url"),null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);  
       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

	@Test
    public void getSuperFundsTest() throws IOException {
        System.out.println("@Test - getSuperFundsTest");
        
        SuperFunds response = payrollAuApi.getSuperfunds(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getSuperFunds().getFirst().getSuperFundID(), is(equalTo(UUID.fromString("fde8e070-bf59-4e56-b1d7-c75a09474b8d"))));        
        assertThat(response.getSuperFunds().getFirst().getType(), is(equalTo(com.xero.models.payrollau.SuperFundType.REGULATED)));
        assertThat(response.getSuperFunds().getFirst().getName(), is(equalTo("Accumulate Plus (Commonwealth Bank Group Super)")));        
        assertThat(response.getSuperFunds().getFirst().getUSI(), is(equalTo("OSF0001AU")));     
        assertThat(response.getSuperFunds().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T22:14:28Z"))));            
        //System.out.println(response.toString());
    }

    @Test
    public void getSuperFundTest() throws IOException {
        System.out.println("@Test - getSuperFundTest");
        
        UUID superFundId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SuperFunds response = payrollAuApi.getSuperfund(accessToken, xeroTenantId, superFundId);
        
        assertThat(response.getSuperFunds().getFirst().getSuperFundID(), is(equalTo(UUID.fromString("540f4327-dda2-4b36-9c2f-2fe1c93a72b5"))));        
        assertThat(response.getSuperFunds().getFirst().getType(), is(equalTo(com.xero.models.payrollau.SuperFundType.SMSF)));
        assertThat(response.getSuperFunds().getFirst().getName(), is(equalTo("My Self Managed one")));        
        assertThat(response.getSuperFunds().getFirst().getABN(), is(equalTo("53004085616")));                
        assertThat(response.getSuperFunds().getFirst().getBSB(), is(equalTo("324324")));        
        assertThat(response.getSuperFunds().getFirst().getAccountNumber(), is(equalTo("234234234")));        
        assertThat(response.getSuperFunds().getFirst().getAccountName(), is(equalTo("My Checking")));        
        assertThat(response.getSuperFunds().getFirst().getElectronicServiceAddress(), is(equalTo("FG48739")));        
        assertThat(response.getSuperFunds().getFirst().getEmployerNumber(), is(equalTo("9876543")));     
        assertThat(response.getSuperFunds().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-12T15:10:29Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void createSuperfundTest() throws IOException {
        System.out.println("@Test - createSuperfundTest");
        
        List<SuperFund> superFunds = new ArrayList<>();        
        SuperFunds response = payrollAuApi.createSuperfund(accessToken, xeroTenantId, superFunds, null);
        
        assertThat(response.getSuperFunds().getFirst().getSuperFundID(), is(equalTo(UUID.fromString("e02e44eb-2dba-4d5e-84da-8a0c3a4a4fef"))));        
        assertThat(response.getSuperFunds().getFirst().getType(), is(equalTo(com.xero.models.payrollau.SuperFundType.REGULATED)));
        assertThat(response.getSuperFunds().getFirst().getName(), is(equalTo("AMG Super")));        
        assertThat(response.getSuperFunds().getFirst().getABN(), is(equalTo("30099320583")));   
        assertThat(response.getSuperFunds().getFirst().getAccountNumber(), is(equalTo("FB36350")));        
        assertThat(response.getSuperFunds().getFirst().getAccountName(), is(equalTo("Foo38428")));        
        assertThat(response.getSuperFunds().getFirst().getUSI(), is(equalTo("PTC0133AU")));
        assertThat(response.getSuperFunds().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-12T18:29:53.009Z"))));    
        //System.out.println(response.toString());
    }

    @Test
    public void updateSuperfundTest() throws IOException {
        System.out.println("@Test - updateSuperfundTest");
        
        UUID superFundId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<SuperFund> superFunds = new ArrayList<>();        
        SuperFunds response = payrollAuApi.updateSuperfund(accessToken, xeroTenantId, superFundId, superFunds, null);
        
        assertThat(response.getSuperFunds().getFirst().getSuperFundID(), is(equalTo(UUID.fromString("fde8e070-bf59-4e56-b1d7-c75a09474b8d"))));        
        assertThat(response.getSuperFunds().getFirst().getType(), is(equalTo(com.xero.models.payrollau.SuperFundType.REGULATED)));
        assertThat(response.getSuperFunds().getFirst().getName(), is(equalTo("Accumulate Plus (Commonwealth Bank Group Super)")));        
        assertThat(response.getSuperFunds().getFirst().getABN(), is(equalTo("24248426878")));        
        assertThat(response.getSuperFunds().getFirst().getUSI(), is(equalTo("OSF0001AU")));        
        assertThat(response.getSuperFunds().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T22:14:28Z")))); 
        //System.out.println(response.toString());
    }
}
