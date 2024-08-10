package entities.UdrGenerator;

import entities.SystemManager.ISystemManager;
import Models.Cdr;
import Models.Udr;
import entities.SystemManager.SystemManager;
import Services.DurationCalculator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс UdrGenerator отвечает за генерацию отчетов на основе данных CDR.
 */
public class UdrGenerator implements IUdrGenerator {

    private final ISystemManager systemManager; // Менеджер системы для работы с базой данных

    /**
     * Конструктор класса.
     *
     * @param connection Соединение с базой данных.
     */
    public UdrGenerator(Connection connection) {
        this.systemManager = new SystemManager(connection);
    }

    /**
     * Генерирует отчет для всех абонентов.
     * Извлекает записи CDR из базы данных, агрегирует их и сохраняет отчет в формате JSON.
     * Также выводит таблицу с агрегированными данными.
     */
    public void generateReport() {
        List<Cdr> cdrs = systemManager.readCdrFromDatabase();
        Map<String, Udr> udrs = aggregateCdrRecords(cdrs);

        String filePath = "reports/all_records.json";
        systemManager.saveReportToFile(new ArrayList<>(udrs.values()), filePath);
        systemManager.printTable(udrs);
    }

    /**
     * Генерирует отчет для конкретного абонента.
     * Извлекает записи CDR из базы данных для указанного абонента, агрегирует их и сохраняет отчет.
     * Также выводит таблицу с данными для указанного абонента.
     *
     * @param msisdn Номер абонента.
     */
    public void generateReport(String msisdn) {
        List<Cdr> cdrs = systemManager.readCdrFromDatabase(msisdn);
        Map<String, Udr> udrs = aggregateCdrRecords(cdrs);

        String filePath = "reports/" + msisdn + ".json";
        systemManager.saveReportToFile(List.of(udrs.get(msisdn)), filePath);

        Map<String, Udr> singleRecordMap = new HashMap<>();
        singleRecordMap.put(msisdn, udrs.get(msisdn));
        systemManager.printTable(singleRecordMap);
    }

    /**
     * Генерирует отчет для конкретного абонента и месяца.
     * Извлекает записи CDR из базы данных для указанного абонента и месяца,
     * Агрегирует их и сохраняет отчет.
     * Также выводит таблицу с данными для указанного абонента и месяца.
     *
     * @param msisdn Номер абонента.
     * @param month Месяц.
     */
    public void generateReport(String msisdn, int month) {
        List<Cdr> cdrs = systemManager.readCdrFromDatabase(msisdn, month);
        Map<String, Udr> udrs = aggregateCdrRecords(cdrs);

        String filePath = "reports/" + msisdn + "_" + month + ".json";
        systemManager.saveReportToFile(new ArrayList<>(udrs.values()), filePath);
        systemManager.printTable(udrs);

    }

    /**
     * Агрегирует записи CDR в объекты UDR.
     *
     * @param cdrs Список записей CDR.
     * @return Map, сопоставляющая номера абонентов с объектами UDR.
     */
    private Map<String, Udr> aggregateCdrRecords(List<Cdr> cdrs) {
        Map<String, Udr> udrs = new HashMap<>();
        for (Cdr cdr : cdrs) {
            String msisdn = cdr.getMsisdn();
            Udr udr = udrs.computeIfAbsent(msisdn, m -> new Udr(m, "00:00:00", "00:00:00"));

            long callDuration = cdr.getEndTime() - cdr.getStartTime();
            String callDurationStr = DurationCalculator.formatDuration(callDuration);

            if ("01".equals(cdr.getCallType())) {
                String currentTotal = udr.getOutgoingCallTotalTime();
                String newTotal = DurationCalculator.sumDurations(currentTotal, callDurationStr);
                udr = new Udr(msisdn, udr.getIncomingCallTotalTime(), newTotal);
                udrs.put(msisdn, udr);
            } else if ("02".equals(cdr.getCallType())) {
                String currentTotal = udr.getIncomingCallTotalTime();
                String newTotal = DurationCalculator.sumDurations(currentTotal, callDurationStr);
                udr = new Udr(msisdn, newTotal, udr.getOutgoingCallTotalTime());
                udrs.put(msisdn, udr);
            }
        }
        return udrs;
    }
}
