package Models;

/**
 * Модель данных для представления записи CDR (Call Data Record).
 */
public class Cdr {
    private String callType; // Тип вызова
    private String msisdn; // Номер абонента
    private long startTime; // Время начала вызова
    private long endTime; // Время окончания вызова

    /**
     * Конструктор класса Cdr.
     *
     * @param callType Тип вызова.
     * @param msisdn Номер абонента.
     * @param startTime Время начала вызова.
     * @param endTime Время окончания вызова.
     */
    public Cdr(String callType, String msisdn, long startTime, long endTime) {
        this.callType = callType;
        this.msisdn = msisdn;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Получить тип вызова.
     *
     * @return Тип вызова.
     */
    public String getCallType() {
        return callType;
    }

    /**
     * Установить тип вызова.
     *
     * @param callType Тип вызова.
     */
    private void setCallType(String callType) {
        this.callType = callType;
    }

    /**
     * Получить номер абонента.
     *
     * @return Номер абонента.
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Установить номер абонента.
     *
     * @param msisdn Номер абонента.
     */
    private void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * Получить время начала вызова.
     *
     * @return Время начала вызова.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Установить время начала вызова.
     *
     * @param startTime Время начала вызова.
     */
    private void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Получить время окончания вызова.
     *
     * @return Время окончания вызова.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Установить время окончания вызова.
     *
     * @param endTime Время окончания вызова.
     */
    private void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Представление объекта Cdr в виде строки.
     *
     * @return Строковое представление объекта Cdr.
     */
    public String toString() {
        return callType + "," + msisdn + "," + startTime + "," + endTime;
    }
}
