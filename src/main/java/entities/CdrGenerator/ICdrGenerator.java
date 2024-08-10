package entities.CdrGenerator;

import models.Cdr;

import java.util.List;

public interface ICdrGenerator {
    void generateCdrFiles();

    List<Cdr> generateCdr(List<String> subscribers);
}
