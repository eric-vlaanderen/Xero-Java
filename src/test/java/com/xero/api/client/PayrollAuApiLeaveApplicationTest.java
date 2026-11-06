package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.LeaveApplication;
import com.xero.models.payrollau.LeaveApplications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollAuApiLeaveApplicationTest {

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
    public void getLeaveApplicationsTest() throws IOException {
        System.out.println("@Test - getLeaveApplicationsTest");
        
        LeaveApplications response = payrollAuApi.getLeaveApplications(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getLeaveApplications().getFirst().getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().getFirst().getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().getFirst().getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().getFirst().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));        
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getNumberOfUnits(), is(equalTo(0.0)));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:30:08Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void getLeaveApplicationTest() throws IOException {
        System.out.println("@Test - getLeaveApplicationTest");
        
        UUID leaveApplicationId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LeaveApplications response = payrollAuApi.getLeaveApplication(accessToken, xeroTenantId, leaveApplicationId);
        
        assertThat(response.getLeaveApplications().getFirst().getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().getFirst().getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().getFirst().getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().getFirst().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));        
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getNumberOfUnits(), is(equalTo(7.6)));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:30:08Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void createLeaveApplicationTest() throws IOException {
        System.out.println("@Test - createLeaveApplicationTest");
        
        List<LeaveApplication> leaveApplications = new ArrayList<>();        
        LeaveApplications response = payrollAuApi.createLeaveApplication(accessToken, xeroTenantId, leaveApplications, null);
        
        assertThat(response.getLeaveApplications().getFirst().getTitle(), is(equalTo("Hello World")));
        assertThat(response.getLeaveApplications().getFirst().getLeaveApplicationID() , is(equalTo(UUID.fromString("5f7097e4-51f2-46cc-921b-45bc73ea7831"))));
        assertThat(response.getLeaveApplications().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().getFirst().getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,10,31))));
        assertThat(response.getLeaveApplications().getFirst().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,1))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,07))));        
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getNumberOfUnits(), is(equalTo(7.6)));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T21:16:31.897Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void updateLeaveApplicationTest() throws IOException {
        System.out.println("@Test - updateLeaveApplicationTest");
        
        UUID leaveApplicationId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<LeaveApplication> leaveApplications = new ArrayList<>();
        LeaveApplications response = payrollAuApi.updateLeaveApplication(accessToken, xeroTenantId, leaveApplicationId, leaveApplications, null);
        
        assertThat(response.getLeaveApplications().getFirst().getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().getFirst().getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().getFirst().getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,10,31))));
        assertThat(response.getLeaveApplications().getFirst().getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().getFirst().getDescription(), is(equalTo("My updated Description")));        
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,07))));        
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getNumberOfUnits(), is(equalTo(7.6)));
        assertThat(response.getLeaveApplications().getFirst().getLeavePeriods().getFirst().getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T21:16:32.293Z"))));  
        //System.out.println(response.toString());
    }
}
