package entities.SystemManager;

import models.Cdr;
import models.Udr;

import java.util.List;
import java.util.Map;

public interface ISystemManager {
    List<Cdr> readCdrFromDatabase();

    List<Cdr> readCdrFromDatabase(String msisdn);

    List<Cdr> readCdrFromDatabase(String msisdn, int month);

    void saveCdrToFiles(List<Cdr> cdrRecords);

    void saveReportToFile(List<Udr> udrRecords, String fileName);

    void printTable(Map<String, Udr> udrRecords);

    List<String> getSubscribersFromDatabase();

}