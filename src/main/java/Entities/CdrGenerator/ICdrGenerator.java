package Entities.CdrGenerator;

import Models.Cdr;

import java.util.List;

public interface ICdrGenerator {
    void generateCdrFiles();

    List<Cdr> generateCdr(List<String> subscribers);
}
