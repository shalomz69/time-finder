package io.daily.service.parser;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class CsvParser<T> implements FileParser<T> {

    public List<T> parse(String csvFile, Class<T> type) throws FileNotFoundException {
        final ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(type);
        strategy.setColumnMapping(Arrays.toString(type.getDeclaredFields()));

        final CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(csvFile))
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(0)
                .build();
        return csvToBean.parse();
    }
}
