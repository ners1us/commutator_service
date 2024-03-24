package Entities.SystemManager;

import Models.Cdr;
import Models.Udr;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс SystemManager отвечает за управление базой данных и генерацию отчетов.
 */
public class SystemManager implements ISystemManager {
    private final Connection connection; // Соединение с базой данных
    private final String TRANSACTIONS_TABLE = "TRANSACTIONS"; // Название таблицы с CDR
    private final String MSISDN_COLUMN = "MSISDN"; // Название столбца с номером абонента в таблице
    private final String CALL_TYPE_COLUMN = "CALL_TYPE"; // Название столбца с типом вызова в таблице
    private final String START_TIME_COLUMN = "START_TIME"; // Название столбца с временем начала вызова в таблице
    private final String END_TIME_COLUMN = "END_TIME"; // Название столбца с временем окончания вызова в таблице

    /**
     * Конструктор класса.
     *
     * @param connection Соединение с базой данных.
     */
    public SystemManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Читает записи CDR.
     *
     * @return Список объектов Cdr, представляющих записи CDR из базы данных.
     */
    public List<Cdr> readCdrFromDatabase() {
        List<Cdr> cdrs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TRANSACTIONS_TABLE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String msisdn = resultSet.getString(MSISDN_COLUMN);
                String callType = resultSet.getString(CALL_TYPE_COLUMN);
                long startTime = resultSet.getLong(START_TIME_COLUMN);
                long endTime = resultSet.getLong(END_TIME_COLUMN);
                cdrs.add(new Cdr(callType, msisdn, startTime, endTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdrs;
    }

    /**
     * Читает записи CDR для указанного абонента.
     *
     * @param msisdn Номер абонента.
     * @return Список объектов Cdr, представляющих записи CDR для указанного абонента.
     */
    public List<Cdr> readCdrFromDatabase(String msisdn) {
        List<Cdr> cdrs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TRANSACTIONS_TABLE + " WHERE " + MSISDN_COLUMN + " = ?")) {
            statement.setString(1, msisdn);
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String callType = resultSet.getString(CALL_TYPE_COLUMN);
                    long startTime = resultSet.getLong(START_TIME_COLUMN);
                    long endTime = resultSet.getLong(END_TIME_COLUMN);
                    cdrs.add(new Cdr(callType, msisdn, startTime, endTime));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdrs;
    }

    /**
     * Читает записи CDR для указанного абонента и месяца.
     *
     * @param msisdn Номер абонента.
     * @param month Месяц.
     * @return Список объектов Cdr, представляющих записи CDR для указанного абонента и месяца.
     */
    public List<Cdr> readCdrFromDatabase(String msisdn, int month) {
        List<Cdr> cdrs = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE msisdn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, msisdn);
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String callType = resultSet.getString(CALL_TYPE_COLUMN);
                    long startTime = resultSet.getLong(START_TIME_COLUMN);
                    long endTime = resultSet.getLong(END_TIME_COLUMN);

                    Date startDate = new Date(startTime);
                    Date endDate = new Date(endTime);

                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.setTime(startDate);
                    int startMonth = startCalendar.get(Calendar.MONTH);

                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(endDate);
                    int endMonth = endCalendar.get(Calendar.MONTH);

                    if (startMonth == month && endMonth == month) {
                        cdrs.add(new Cdr(callType, msisdn, startTime, endTime));
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cdrs;
    }

    /**
     * Сохраняет записи CDR в файл.
     *
     * @param cdrs Список объектов Cdr, которые нужно сохранить.
     */
    public void saveCdrToFiles(List<Cdr> cdrs) {
        try (FileWriter fileWriter = new FileWriter("data/cdr_records.txt")) {
            for (Cdr cdr : cdrs) {
                fileWriter.write(cdr.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохраняет отчет о CDR.
     *
     * @param udrs Список объектов Udr, представляющих отчет о CDR.
     * @param fileName Имя файла, в который нужно сохранить отчет.
     */
    public void saveReportToFile(List<Udr> udrs, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(udrs, fileWriter);
            System.out.println("Saved report to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выводит таблицу с данными об абонентах и их длительности звонков.
     *
     * @param udrs Словарь с данными об абонентах и их длительности звонков.
     */
    public void printTable(Map<String, Udr> udrs) {
        System.out.println("MSISDN\t\tIncoming Call Time\t\tOutgoing Call Time");
        for (Udr udrRecord : udrs.values()) {
            System.out.printf("%s\t\t%s\t\t%s%n", udrRecord.getMsisdn(),
                    udrRecord.getIncomingCallTotalTime(), udrRecord.getOutgoingCallTotalTime());
        }
    }

    /**
     * Получает список subscribers.
     *
     * @return список subscribers.
     */
    public List<String> getSubscribersFromDatabase() {
        List<String> subscribers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT msisdn FROM subscribers");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                subscribers.add(resultSet.getString("msisdn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscribers;
    }

}
