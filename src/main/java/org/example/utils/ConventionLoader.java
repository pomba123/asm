package org.example.utils;

import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ConventionLoader {
    public static List<Convention> loadConventions(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ConventionMapping mapping = mapper.readValue(Paths.get(path).toFile(), ConventionMapping.class);
        return mapping.getConventions();
    }
}
