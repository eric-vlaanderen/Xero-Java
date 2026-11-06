package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.Employee;
import com.xero.models.payrollau.Employees;
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

public class PayrollAuApiEmployeeTest {

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
    public void getEmployeesTest() throws IOException {
        System.out.println("@Test - getEmployeesTest");
        
        Employees response = payrollAuApi.getEmployees(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getEmployees().getFirst().getFirstName(), is(equalTo("Jack")));
        assertThat(response.getEmployees().getFirst().getLastName(), is(equalTo("Sparrow")));
        assertThat(response.getEmployees().getFirst().getDateOfBirthAsDate(), is(equalTo(LocalDate.of(1988, 2, 20))));
        assertThat(response.getEmployees().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(2019,1,11))));
        assertThat(response.getEmployees().getFirst().getMiddleNames(), is(equalTo("Johnson")));
        assertThat(response.getEmployees().getFirst().getEmail(), is(equalTo("jack.sparrow@xero.com")));
        assertThat(response.getEmployees().getFirst().getGender(), is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().getFirst().getMobile(), is(equalTo("415-1234567")));
        assertThat(response.getEmployees().getFirst().getPhone(), is(equalTo("4153332323")));
        assertThat(response.getEmployees().getFirst().getOrdinaryEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getEmployees().getFirst().getPayrollCalendarID(), is(equalTo(UUID.fromString("22a05fc5-386d-4950-9842-3e7a6c812135"))));
        assertThat(response.getEmployees().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));
        assertThat(response.getEmployees().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:34Z"))));  
        assertThat(response.getEmployees().getFirst().getStatus(), is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeTest() throws IOException {
        System.out.println("@Test - createEmployeeTest");
        
        List<Employee> employee = new ArrayList<>();
        Employees response = payrollAuApi.createEmployee(accessToken, xeroTenantId, employee, null);
        
        assertThat(response.getEmployees().getFirst().getFirstName(), is(equalTo("Albus")));
        assertThat(response.getEmployees().getFirst().getLastName(), is(equalTo("Dumbledore")));
        assertThat(response.getEmployees().getFirst().getDateOfBirthAsDate(), is(equalTo(LocalDate.of(1980, 3, 10))));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getAddressLine1(), is(equalTo("101 Green St")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCity(), is(equalTo("Island Bay")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getRegion(), is(equalTo(com.xero.models.payrollau.State.NSW)));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getPostalCode(), is(equalTo("6023")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
        assertThat(response.getEmployees().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(1980,3,10))));
        assertThat(response.getEmployees().getFirst().getMiddleNames(), is(equalTo("Percival")));
        assertThat(response.getEmployees().getFirst().getEmail(), is(equalTo("albus39608@hogwarts.edu")));
        assertThat(response.getEmployees().getFirst().getGender(), is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().getFirst().getMobile(), is(equalTo("555-1212")));
        assertThat(response.getEmployees().getFirst().getPhone(), is(equalTo("444-2323")));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveLeave(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getJobTitle(), is(equalTo("Regional Manager")));
        assertThat(response.getEmployees().getFirst().getClassification(), is(equalTo("corporate")));
        assertThat(response.getEmployees().getFirst().getOrdinaryEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getEmployees().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getEmployees().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:05:24.458Z"))));  
        assertThat(response.getEmployees().getFirst().getStatus(), is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void getEmployeeTest() throws IOException {
        System.out.println("@Test - getEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        Employees response = payrollAuApi.getEmployee(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getEmployees().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getEmployees().getFirst().getFirstName(), is(equalTo("Albus")));
        assertThat(response.getEmployees().getFirst().getLastName(), is(equalTo("Dumbledore")));
        assertThat(response.getEmployees().getFirst().getDateOfBirthAsDate(), is(equalTo(LocalDate.of(1980, 3, 10))));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getAddressLine1(), is(equalTo("101 Green St")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCity(), is(equalTo("Island Bay")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getRegion(), is(equalTo(com.xero.models.payrollau.State.NSW)));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getPostalCode(), is(equalTo("6023")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
        assertThat(response.getEmployees().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(1980,3, 10))));
        assertThat(response.getEmployees().getFirst().getTitle(), is(equalTo("Mr.")));
        assertThat(response.getEmployees().getFirst().getMiddleNames(), is(equalTo("Frank")));
        assertThat(response.getEmployees().getFirst().getEmail(), is(equalTo("albus39608@hogwarts.edu")));
        assertThat(response.getEmployees().getFirst().getGender(), is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().getFirst().getMobile(), is(equalTo("555-1212")));
        assertThat(response.getEmployees().getFirst().getPhone(), is(equalTo("444-2323")));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveLeave(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getJobTitle(), is(equalTo("Regional Manager")));
        assertThat(response.getEmployees().getFirst().getClassification(), is(equalTo("corporate")));
        assertThat(response.getEmployees().getFirst().getPayrollCalendarID(), equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de")));
        assertThat(response.getEmployees().getFirst().getEmployeeGroupName(), is(equalTo("foo")));
        // PAY TEMPLATE > EARNINGS LINES
        assertThat(response.getEmployees().getFirst().getPayTemplate().getEarningsLines().getFirst().getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getEarningsLines().getFirst().getCalculationType(), is(equalTo(com.xero.models.payrollau.EarningsRateCalculationType.USEEARNINGSRATE)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getEarningsLines().getFirst().getNormalNumberOfUnits(), is(equalTo(3.0)));
        // PAY TEMPLATE > DEDUCTION LINES
        assertThat(response.getEmployees().getFirst().getPayTemplate().getDeductionLines().getFirst().getDeductionTypeID(), is(equalTo(UUID.fromString("ed05ea82-e40a-4eb6-9c2e-4b3c03e7e938"))));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getDeductionLines().getFirst().getCalculationType(), is(equalTo(com.xero.models.payrollau.DeductionTypeCalculationType.FIXEDAMOUNT)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getDeductionLines().getFirst().getAmount(), is(equalTo(4.0)));
        //PAY TEMPLATE > SUPERLINES
        assertThat(response.getEmployees().getFirst().getPayTemplate().getSuperLines().getFirst().getMinimumMonthlyEarnings(), is(equalTo(450.0)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getSuperLines().getFirst().getExpenseAccountCode(), is(equalTo("478")));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getSuperLines().getFirst().getLiabilityAccountCode(), is(equalTo("826")));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getSuperLines().getFirst().getCalculationType(), is(equalTo(com.xero.models.payrollau.SuperannuationCalculationType.STATUTORY)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getSuperLines().getFirst().getContributionType(), is(equalTo(com.xero.models.payrollau.SuperannuationContributionType.SGC)));        
        // PAY TEMPLATE > REMIMBURSEMENT
        assertThat(response.getEmployees().getFirst().getPayTemplate().getReimbursementLines().getFirst().getReimbursementTypeID(), is(equalTo(UUID.fromString("aa8cfa40-d872-4be0-8a94-bb7f00962f74"))));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getReimbursementLines().getFirst().getAmount(), is(equalTo(55.0)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getReimbursementLines().getFirst().getDescription(), is(equalTo("boo")));
        
        // PAY TEMPLATE > LEAVE LINES
        assertThat(response.getEmployees().getFirst().getPayTemplate().getLeaveLines().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getLeaveLines().getFirst().getCalculationType(), is(equalTo(com.xero.models.payrollau.LeaveLineCalculationType.FIXEDAMOUNTEACHPERIOD)));
        assertThat(response.getEmployees().getFirst().getPayTemplate().getLeaveLines().getFirst().getEntitlementFinalPayPayoutType(), is(equalTo(com.xero.models.payrollau.EntitlementFinalPayPayoutType.NOTPAIDOUT)));        
        
        // OPENING BALANCES
        assertThat(response.getEmployees().getFirst().getOpeningBalances().getOpeningBalanceDateAsDate(), is(equalTo(LocalDate.of(2019,11,13))));
        // OPENING BALANCES > LEAVE LINES
        assertThat(response.getEmployees().getFirst().getOpeningBalances().getLeaveLines().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getEmployees().getFirst().getOpeningBalances().getLeaveLines().getFirst().getNumberOfUnits(), is(equalTo(10.0)));        
        
        // LEAVE BALANCES
        assertThat(response.getEmployees().getFirst().getLeaveBalances().getFirst().getLeaveName(), is(equalTo("Carer Leave (unpaid)")));
        assertThat(response.getEmployees().getFirst().getLeaveBalances().getFirst().getNumberOfUnits(), is(equalTo(10.0)));
        assertThat(response.getEmployees().getFirst().getLeaveBalances().getFirst().getTypeOfUnits(), is(equalTo("Hours")));
        // UUID showing as Boolean?
        //assertThat(response.getEmployees().getFirst().getLeaveBalances().getFirst().getLeaveTypeID(), is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        
        // TAX DECLARATION
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getAustralianResidentForTaxPurposes(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getTaxFreeThresholdClaimed(), is(equalTo(true)));   
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getHasHELPDebt(), is(equalTo(false)));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getHasSFSSDebt(), is(equalTo(false)));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getEligibleToReceiveLeaveLoading(), is(equalTo(false)));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:35:06Z"))));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getHasStudentStartupLoan(), is(equalTo(false)));
        assertThat(response.getEmployees().getFirst().getTaxDeclaration().getResidencyStatus(), is(equalTo(com.xero.models.payrollau.ResidencyStatus.AUSTRALIANRESIDENT)));        
        assertThat(response.getEmployees().getFirst().getOrdinaryEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getEmployees().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:35:06Z"))));  
        assertThat(response.getEmployees().getFirst().getStatus(), is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeTest() throws IOException {
        System.out.println("@Test - updateEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<Employee> employees = new ArrayList<>();
        Employees response = payrollAuApi.updateEmployee(accessToken, xeroTenantId, employeeId, employees, null);
        
        assertThat(response.getEmployees().getFirst().getFirstName(), is(equalTo("Albus")));
        assertThat(response.getEmployees().getFirst().getLastName(), is(equalTo("Dumbledore")));
        assertThat(response.getEmployees().getFirst().getDateOfBirthAsDate(), is(equalTo(LocalDate.of(1980, 3, 10))));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getAddressLine1(), is(equalTo("101 Green St")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCity(), is(equalTo("Island Bay")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getRegion(), is(equalTo(com.xero.models.payrollau.State.NSW)));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getPostalCode(), is(equalTo("6023")));
        assertThat(response.getEmployees().getFirst().getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
        assertThat(response.getEmployees().getFirst().getStartDateAsDate(), is(equalTo(LocalDate.of(1980,3,10))));
        assertThat(response.getEmployees().getFirst().getMiddleNames(), is(equalTo("Frank")));
        assertThat(response.getEmployees().getFirst().getEmail(), is(equalTo("albus39608@hogwarts.edu")));
        assertThat(response.getEmployees().getFirst().getGender(), is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().getFirst().getMobile(), is(equalTo("555-1212")));
        assertThat(response.getEmployees().getFirst().getPhone(), is(equalTo("444-2323")));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveLeave(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
        assertThat(response.getEmployees().getFirst().getJobTitle(), is(equalTo("Regional Manager")));
        assertThat(response.getEmployees().getFirst().getClassification(), is(equalTo("corporate")));
        assertThat(response.getEmployees().getFirst().getOrdinaryEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getEmployees().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getEmployees().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:05:24.755Z"))));  
        assertThat(response.getEmployees().getFirst().getStatus(), is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
        //System.out.println(response.toString());
    }
}
