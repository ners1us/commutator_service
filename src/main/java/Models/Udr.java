package Models;

/**
 * Модель данных для представления записи UDR (Usage Data Report).
 */
public class Udr {
    private String msisdn; // Номер абонента
    private String incomingCallTotalTime; // Общая длительность входящих звонков
    private String outgoingCallTotalTime; // Общая длительность исходящих звонков

    /**
     * Конструктор для создания объекта UDR с указанием MSISDN и общих длительностей звонков.
     *
     * @param msisdn Номер абонента.
     * @param incomingCallTotalTime Общая длительность входящих звонков.
     * @param outgoingCallTotalTime Общая длительность исходящих звонков.
     */
    public Udr(String msisdn, String incomingCallTotalTime, String outgoingCallTotalTime) {
        this.msisdn = msisdn;
        this.incomingCallTotalTime = incomingCallTotalTime;
        this.outgoingCallTotalTime = outgoingCallTotalTime;
    }

    /**
     * Получает номер абонента.
     *
     * @return Номер абонента.
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Устанавливает номер абонента.
     *
     * @param msisdn Номер абонента.
     */
    private void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * Получает общую длительность входящих звонков.
     *
     * @return Общая длительность входящих звонков.
     */
    public String getIncomingCallTotalTime() {
        return incomingCallTotalTime;
    }

    /**
     * Устанавливает общую длительность входящих звонков.
     *
     * @param incomingCallTotalTime Общая длительность входящих звонков.
     */
    private void setIncomingCallTotalTime(String incomingCallTotalTime) {
        this.incomingCallTotalTime = incomingCallTotalTime;
    }

    /**
     * Получает общую длительность исходящих звонков.
     *
     * @return Общая длительность исходящих звонков.
     */
    public String getOutgoingCallTotalTime() {
        return outgoingCallTotalTime;
    }

    /**
     * Устанавливает общую длительность исходящих звонков.
     *
     * @param outgoingCallTotalTime Общая длительность исходящих звонков.
     */
    private void setOutgoingCallTotalTime(String outgoingCallTotalTime) {
        this.outgoingCallTotalTime = outgoingCallTotalTime;
    }
}
