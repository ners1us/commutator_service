import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import entities.CdrGenerator.ICdrGenerator;
import Models.Udr;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.CdrGenerator.CdrGenerator;
import Models.Cdr;

public class CdrAndUdrTests {
    private ICdrGenerator cdrGenerator;

    @BeforeEach
    public void setUp() {
        cdrGenerator = new CdrGenerator(null);
    }

    @Test
    public void testGenerateCdr() {
        List<String> subscribers = Arrays.asList("79541134455", "7987654321");
        List<Cdr> cdrRecords = cdrGenerator.generateCdr(subscribers);

        assertEquals(20, cdrRecords.size());
    }

    @Test
    public void testGenerateCdrWithEmptySubscribers() {
        List<String> subscribers = List.of();
        List<Cdr> cdrRecords = cdrGenerator.generateCdr(subscribers);

        assertTrue(cdrRecords.isEmpty());
    }

    @Test
    void testUdrConstructorAndGetters() {
        Udr udr = new Udr("7934567890", "01:30:00", "02:15:00");

        assertEquals("7934567890", udr.getMsisdn());
        assertEquals("01:30:00", udr.getIncomingCallTotalTime());
        assertEquals("02:15:00", udr.getOutgoingCallTotalTime());
    }
}
