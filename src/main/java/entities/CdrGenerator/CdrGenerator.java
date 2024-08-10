package entities.CdrGenerator;

import entities.SystemManager.ISystemManager;
import Models.Cdr;
import entities.SystemManager.SystemManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс CdrGenerator отвечает за генерацию записей CDR.
 */
public class CdrGenerator implements ICdrGenerator {
    private final ISystemManager systemManager; // Менеджер системы для работы с базой данных

    /**
     * Конструктор класса.
     *
     * @param dataSource Соединение с базой данных.
     */
    public CdrGenerator(Connection dataSource) {
        this.systemManager = new SystemManager(dataSource);
    }

    /**
     * Генерирует файлы с записями CDR для всех абонентов.
     * Получает список абонентов из базы данных и для каждого абонента генерирует записи CDR,
     * а после сохраняет их в файлы.
     */
    public void generateCdrFiles() {
        List<String> subscribers = systemManager.getSubscribersFromDatabase();
        List<Cdr> cdrs = generateCdr(subscribers);
        systemManager.saveCdrToFiles(cdrs);
    }

    /**
     * Генерирует записи CDR для указанных абонентов.
     *
     * @param subscribers Список абонентов.
     * @return Список объектов Cdr, представляющих собой сгенерированные записи CDR.
     */
    public List<Cdr> generateCdr(List<String> subscribers) {
        List<Cdr> cdrs = new ArrayList<>();
        Random random = new Random();
        for (String subscriber : subscribers) {
            for (int i = 0; i < 10; i++) {
                String callType = random.nextBoolean() ? "01" : "02";
                long startTime = System.currentTimeMillis() - random.nextInt(10000000);
                long endTime = startTime + random.nextInt(600000);
                cdrs.add(new Cdr(callType, subscriber, startTime, endTime));
            }
        }
        return cdrs;
    }
}
