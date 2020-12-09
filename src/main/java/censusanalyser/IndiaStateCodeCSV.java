package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Code", required = true)
    public int code;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "state='" + state + '\'' +
                ", code=" + code +
                '}';
    }
}
