package io.daily.service.parser;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileParser<T> {

    List<T> parse(String filePath, Class<T> type) throws FileNotFoundException;
}

