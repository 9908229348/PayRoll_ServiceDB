package addressbook;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayRollTest {

    @Test
    public void givenDataBaseConnection_ReturnDataFromDataBase(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<PayRollData> list = employeePayRollService.readEmployeePayRoll();
        System.out.println(list);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<PayRollData> payRollDataList = employeePayRollService.readEmployeePayRoll();
        employeePayRollService.updateEmployeeSalary("Terisa",3000000.00);
        boolean result = employeePayRollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRoll();
        LocalDate startDate = LocalDate.of(2019,01,01);
        LocalDate endDate = LocalDate.now();
        List<PayRollData> employeePayRollData = employeePayRollService.readEmployeePayRollForDateRange(startDate, endDate);
        Assert.assertEquals(2, employeePayRollData.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetrieveByGender_ShouldReturnProperValue(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRoll();
        Map<String, Double> averageSalaryByGender = employeePayRollService.readAverageSalaryByGender();
        System.out.println(averageSalaryByGender);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(2000000.00) && averageSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenPayrollData_WhenCountByGender_ShouldReturnCount(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRoll();
        Map<String, Integer> countByGender = employeePayRollService.readCountSalaryByGender();
        System.out.println(countByGender);
        Assert.assertTrue(countByGender.get("M").equals(3) && countByGender.get("F").equals(1));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB(){
        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayRoll();
        employeePayRollService.addEmployeePayroll("Mark", 5000000.00, LocalDate.now(), "M");
        boolean result = employeePayRollService.checkEmployeePayrollInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }
}
