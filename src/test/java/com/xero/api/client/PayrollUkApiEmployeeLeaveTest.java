package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.EmployeeLeave;
import com.xero.models.payrolluk.EmployeeLeaveObject;
import com.xero.models.payrolluk.EmployeeLeaves;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PayrollUkApiEmployeeLeaveTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrolluk.api.url"),null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);      
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

	@Test
    public void getEmployeeLeavesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeLeavesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaves response =  payrollUkApi.getEmployeeLeaves(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getLeave().getFirst().getLeaveID(), is(equalTo(UUID.fromString("384bf62a-5884-484d-b675-a3ad9c662f8e"))));
        assertThat(response.getLeave().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeave().getFirst().getDescription(), is(equalTo("Vacation to Majorca")));
        assertThat(response.getLeave().getFirst().getStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
        assertThat(response.getLeave().getFirst().getEndDate(), is(equalTo(LocalDate.of(2020, 02, 21))));
        assertThat(response.getLeave().getFirst().getPeriods().getFirst().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
        assertThat(response.getLeave().getFirst().getPeriods().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 02, 23))));
        assertThat(response.getLeave().getFirst().getPeriods().getFirst().getNumberOfUnits(), is(equalTo(40.0)));
        assertThat(response.getLeave().getFirst().getPeriods().getFirst().getPeriodStatus(), is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getFirst().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 15, 53) )));  
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeLeaveTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmployeeLeaveTest");
        
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setLeaveTypeID(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"));
        employeeLeave.setDescription("Creating a Description");
        employeeLeave.setStartDate(LocalDate.of(2020, 04, 24));
        employeeLeave.setEndDate(LocalDate.of(2020, 04, 26));
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        
        EmployeeLeaveObject response = payrollUkApi.createEmployeeLeave(accessToken, xeroTenantId, employeeId, employeeLeave, null);
        
        assertThat(response.getLeave().getLeaveID(), is(equalTo(UUID.fromString("bdfedc6b-363b-4801-8ba6-9a074d400b31"))));
        assertThat(response.getLeave().getLeaveTypeID(), is(equalTo(UUID.fromString("1d2778ee-86ea-45c0-bbf8-1045485f6b3f"))));
        assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Description")));
        assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 03, 24))));
        assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 03, 26))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 03, 23))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 03, 29))));
        assertThat(response.getLeave().getPeriods().getFirst().getNumberOfUnits(), is(equalTo(24.0)));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStatus(), is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 03, 31, 04, 32, 56, 953785700) )));  
       
        //System.out.println(response.toString());
    }

    @Test
    public void getEmployeeLeaveTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeLeaveTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID leaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveObject response = payrollUkApi.getEmployeeLeave(accessToken, xeroTenantId, employeeId, leaveId);
        
        assertThat(response.getLeave().getLeaveID(), is(equalTo(UUID.fromString("384bf62a-5884-484d-b675-a3ad9c662f8e"))));
        assertThat(response.getLeave().getLeaveTypeID(), is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeave().getDescription(), is(equalTo("Vacation to Majorca")));
        assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
        assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 02, 21))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 02, 23))));
        assertThat(response.getLeave().getPeriods().getFirst().getNumberOfUnits(), is(equalTo(40.0)));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStatus(), is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 15, 53) )));  
       
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeLeaveTest() throws IOException {
        System.out.println("@Test UK Payroll - updateEmployeeLeaveTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID leaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setLeaveTypeID(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"));
        employeeLeave.setDescription("Creating a Description");
        employeeLeave.setStartDate(LocalDate.of(2020, 04, 24));
        employeeLeave.setEndDate(LocalDate.of(2020, 04, 26));
        EmployeeLeaveObject response = payrollUkApi.updateEmployeeLeave(accessToken, xeroTenantId, employeeId, leaveId, employeeLeave, null);
        
        assertThat(response.getLeave().getLeaveID(), is(equalTo(UUID.fromString("8340b795-50c1-428e-9fda-90badf081ab4"))));
        assertThat(response.getLeave().getLeaveTypeID(), is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Description")));
        assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 24))));
        assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 04, 20))));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().getFirst().getNumberOfUnits(), is(equalTo(1.0)));
        assertThat(response.getLeave().getPeriods().getFirst().getPeriodStatus(), is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 04, 17, 18, 18, 30).plusNanos(614537000) ))); 
        
        //System.out.println(response.toString());
    }
}
