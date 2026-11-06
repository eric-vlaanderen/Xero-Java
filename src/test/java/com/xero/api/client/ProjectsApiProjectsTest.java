package com.xero.api.client;

import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.project.Project;
import com.xero.models.project.ProjectCreateOrUpdate;
import com.xero.models.project.Projects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ProjectsApiProjectsTest {

	ApiClient defaultClient; 
    ProjectApi projectApi; 
	String accessToken;
    String xeroTenantId; 
   
    private static boolean setUpIsDone = false;
	
	@BeforeEach
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("projects.api.url"),null,null,null,null);
        projectApi = ProjectApi.getInstance(defaultClient); 
       
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
		projectApi = null;
        defaultClient = null;
	}

	@Test
    public void getProjectsTest() throws IOException {
        System.out.println("@Test - getProjectsTest");
        List<UUID> projectIds = null;
        UUID contactID = null;
        String states = null;
        int page = 1;
        int pageSize = 50;
        Projects response = projectApi.getProjects(accessToken, xeroTenantId, projectIds, contactID, states, page, pageSize);
        
        assertThat(response.getPagination().getPage(), is(equalTo(1)));
        assertThat(response.getPagination().getItemCount(), is(equalTo(9)));
        assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
        assertThat(response.getPagination().getPageSize(), is(equalTo(50)));
        assertThat(response.getItems().getFirst().getContactId(), is(equalTo(UUID.fromString("216830cb-9a68-487e-928b-c1a7ccc4fc81"))));
        assertThat(response.getItems().getFirst().getProjectId(), is(equalTo(UUID.fromString("b021e7cb-1903-4292-b48b-5b27b4271e3e"))));
        assertThat(response.getItems().getFirst().getName(), is(equalTo("FooProject28916")));
        assertThat(response.getItems().getFirst().getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getMinutesLogged(), is(equalTo(180)));
        assertThat(response.getItems().getFirst().getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getTotalTaskAmount().getValue(), is(equalTo(60.0)));
        assertThat(response.getItems().getFirst().getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getTotalExpenseAmount().getValue(), is(equalTo(499.0)));
        assertThat(response.getItems().getFirst().getMinutesToBeInvoiced(), is(equalTo(180)));
        assertThat(response.getItems().getFirst().getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getExpenseAmountToBeInvoiced().getValue(), is(equalTo(499.0)));
        assertThat(response.getItems().getFirst().getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().getFirst().getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getTotalToBeInvoiced().getValue(), is(equalTo(559.0)));
        assertThat(response.getItems().getFirst().getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().getFirst().getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getItems().getFirst().getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));
        
        // System.out.println(response.toString());
    }

    @Test
    public void createProjectsTest() throws IOException {
        System.out.println("@Test - createProjectsTest");

        ProjectCreateOrUpdate projectCreateOrUpdate = new ProjectCreateOrUpdate();
        projectCreateOrUpdate.setName("New Kitchen");
        Project response = projectApi.createProject(accessToken, xeroTenantId, projectCreateOrUpdate, null);
        
        assertThat(response.getContactId(), is(equalTo(UUID.fromString("216830cb-9a68-487e-928b-c1a7ccc4fc81"))));
        assertThat(response.getProjectId(), is(equalTo(UUID.fromString("ed957eee-bc6f-4f52-a663-aa42e6af9620"))));
        assertThat(response.getName(), is(equalTo("New Kitchen")));
        assertThat(response.getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getMinutesLogged(), is(equalTo(0)));
        assertThat(response.getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalTaskAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalExpenseAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getMinutesToBeInvoiced(), is(equalTo(0)));
        assertThat(response.getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountToBeInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalToBeInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));        
        assertThat(response.getStatus(), is(equalTo(com.xero.models.project.ProjectStatus.INPROGRESS)));

       // System.out.println(response.toString());
    }

    @Test
    public void getProjectTest() throws IOException {
        System.out.println("@Test - getProjectTest");
        UUID projectId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Project response = projectApi.getProject(accessToken, xeroTenantId, projectId);
        
        assertThat(response.getContactId(), is(equalTo(UUID.fromString("216830cb-9a68-487e-928b-c1a7ccc4fc81"))));
        assertThat(response.getProjectId(), is(equalTo(UUID.fromString("b021e7cb-1903-4292-b48b-5b27b4271e3e"))));
        assertThat(response.getName(), is(equalTo("Remodeling 2012")));
        assertThat(response.getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getMinutesLogged(), is(equalTo(180)));
        assertThat(response.getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalTaskAmount().getValue(), is(equalTo(60.0)));
        assertThat(response.getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalExpenseAmount().getValue(), is(equalTo(499.0)));
        assertThat(response.getMinutesToBeInvoiced(), is(equalTo(180)));
        assertThat(response.getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountToBeInvoiced().getValue(), is(equalTo(499.0)));
        assertThat(response.getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalToBeInvoiced().getValue(), is(equalTo(559.0)));
        assertThat(response.getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));
        
        // System.out.println(response.toString());
    }
}
