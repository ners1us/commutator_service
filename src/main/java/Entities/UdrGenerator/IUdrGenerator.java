package Entities.UdrGenerator;

public interface IUdrGenerator {
    void generateReport();
    void generateReport(String msisdn);
    void generateReport(String msisdn, int month);
}
