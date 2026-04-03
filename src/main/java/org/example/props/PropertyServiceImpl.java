package org.example.props;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyServiceImpl implements PropertyService {



    @Override
    public Map<String, String> getApplicationProperyMaps() throws FileNotFoundException {
        String path = ClassLoader.getSystemClassLoader().getResource("apllication.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        Map<String, String> map = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
        return map;
    }
}
