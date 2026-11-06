package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollau.PayslipObject;
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

public class PayrollAuApiPayslipTest {

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
    public void getPayslipTest() throws IOException {
        System.out.println("@Test - getPayslipTest");
        
        UUID payslipId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        PayslipObject response = payrollAuApi.getPayslip(accessToken, xeroTenantId, payslipId);
        
        assertThat(response.getPayslip().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getPayslip().getPayslipID(), is(equalTo(UUID.fromString("c81e8bcc-56b0-4740-b46b-767753a6ee45"))));
        assertThat(response.getPayslip().getFirstName(), is(equalTo("Albus")));
        assertThat(response.getPayslip().getLastName(), is(equalTo("Dumbledore")));
        assertThat(response.getPayslip().getTax(), is(equalTo(0.0)));
        assertThat(response.getPayslip().getNetPay(), is(equalTo(1.4)));
        assertThat(response.getPayslip().getEarningsLines().getFirst().getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getPayslip().getEarningsLines().getFirst().getRatePerUnit(), is(equalTo(3.0)));
        assertThat(response.getPayslip().getEarningsLines().getFirst().getNumberOfUnits(), is(equalTo(1.8)));        
        assertThat(response.getPayslip().getLeaveEarningsLines().getFirst().getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getPayslip().getLeaveEarningsLines().getFirst().getRatePerUnit(), is(equalTo(0.0)));
        assertThat(response.getPayslip().getLeaveEarningsLines().getFirst().getNumberOfUnits(), is(equalTo(0.6)));
        assertThat(response.getPayslip().getDeductionLines().getFirst().getDeductionTypeID() , is(equalTo(UUID.fromString("ed05ea82-e40a-4eb6-9c2e-4b3c03e7e938"))));
        assertThat(response.getPayslip().getDeductionLines().getFirst().getAmount() , is(equalTo(4.0)));
        assertThat(response.getPayslip().getDeductionLines().getFirst().getCalculationType() , is(equalTo(com.xero.models.payrollau.DeductionTypeCalculationType.FIXEDAMOUNT)));
        assertThat(response.getPayslip().getLeaveAccrualLines().getFirst().getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getPayslip().getLeaveAccrualLines().getFirst().getNumberOfUnits(), is(equalTo(0.0769)));
        assertThat(response.getPayslip().getLeaveAccrualLines().getFirst().getAutoCalculate(), is(equalTo(true)));        
        assertThat(response.getPayslip().getReimbursementLines().getFirst().getReimbursementTypeID() , is(equalTo(UUID.fromString("aa8cfa40-d872-4be0-8a94-bb7f00962f74"))));
        assertThat(response.getPayslip().getReimbursementLines().getFirst().getAmount(), is(equalTo(55.0)));
        assertThat(response.getPayslip().getReimbursementLines().getFirst().getDescription(), is(equalTo("boo")));
        assertThat(response.getPayslip().getReimbursementLines().getFirst().getExpenseAccount(), is(equalTo("850")));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getContributionType(), is(equalTo(com.xero.models.payrollau.SuperannuationContributionType.SGC)));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getCalculationType(), is(equalTo(com.xero.models.payrollau.SuperannuationCalculationType.STATUTORY)));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getMinimumMonthlyEarnings(), is(equalTo(450.0)));        
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getExpenseAccountCode(), is(equalTo("478")));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getLiabilityAccountCode(), is(equalTo("826")));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getPaymentDateForThisPeriodAsDate(), is(equalTo(LocalDate.of(2020,01,28))));
        assertThat(response.getPayslip().getSuperannuationLines().getFirst().getAmount(), is(equalTo(0.0)));
        assertThat(response.getPayslip().getTaxLines().getFirst().getPayslipTaxLineID(), is(equalTo(UUID.fromString("c129696e-36ef-4677-a54c-96095787ca20"))));
        assertThat(response.getPayslip().getTaxLines().getFirst().getAmount(), is(equalTo(0.0)));
        assertThat(response.getPayslip().getTaxLines().getFirst().getTaxTypeName(), is(equalTo("PAYG Tax")));
        assertThat(response.getPayslip().getTaxLines().getFirst().getDescription(), is(equalTo("No tax file number (Australian resident)")));
        assertThat(response.getPayslip().getTaxLines().getFirst().getLiabilityAccount(), is(equalTo("825")));
        assertThat(response.getPayslip().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-14T00:42:35Z"))));          
  
        //System.out.println(response.toString());
    }

    @Test
    public void updatePayslipTest() throws IOException {
        System.out.println("@Test - updatePayslipDTest");
        
        UUID payslipId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<com.xero.models.payrollau.PayslipLines> payslipObject = new ArrayList<>(); 
        com.xero.models.payrollau.Payslips response = payrollAuApi.updatePayslip(accessToken, xeroTenantId, payslipId, payslipObject, null);
        
// Can not write test until I can 
// make the call and get a live response back for the OAS to use


        assertThat(response.getPayslips().getFirst().getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getPayslips().getFirst().getPayslipID(), is(equalTo(UUID.fromString("c81e8bcc-56b0-4740-b46b-767753a6ee45"))));        
        assertThat(response.getPayslips().getFirst().getFirstName(), is(equalTo("Albus")));
        assertThat(response.getPayslips().getFirst().getLastName(), is(equalTo("Dumbledore")));
        assertThat(response.getPayslips().getFirst().getTax(), is(equalTo(0.0)));
        assertThat(response.getPayslips().getFirst().getNetPay(), is(equalTo(1.4)));
        assertThat(response.getPayslips().getFirst().getDeductionLines().getFirst().getDeductionTypeID() , is(equalTo(UUID.fromString("ed05ea82-e40a-4eb6-9c2e-4b3c03e7e938"))));
        assertThat(response.getPayslips().getFirst().getDeductionLines().getFirst().getAmount() , is(equalTo(4.0)));
        assertThat(response.getPayslips().getFirst().getDeductionLines().getFirst().getCalculationType() , is(equalTo(com.xero.models.payrollau.DeductionTypeCalculationType.FIXEDAMOUNT)));
        assertThat(response.getPayslips().getFirst().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2020-05-15T18:44:13.735Z"))));          
        //System.out.println(response.toString());
    }

}
