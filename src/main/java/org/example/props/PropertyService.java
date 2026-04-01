package org.example.props;

import java.io.FileNotFoundException;
import java.util.Map;

public interface PropertyService {

    Map<String, String> getApplicationProperyMaps() throws FileNotFoundException;
}
