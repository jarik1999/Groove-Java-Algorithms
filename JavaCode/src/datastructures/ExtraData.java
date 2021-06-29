package datastructures;

import javax.lang.model.UnknownEntityException;
import javax.lang.model.type.UnknownTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtraData {
    Map<String, DataValue> data = new HashMap<>();
    public ExtraData(List<String> entries){
        for (String e : entries){
            try {
                parseEntry(e);
            } catch (Exception exception) {
                System.err.println("ExtraData received something it couldn't parse!");
                System.err.println(e);
                exception.printStackTrace();
            }
        }
    }
    public Map<String, DataValue> getData(){
        return data;
    }
    public DataValue get(String key){
        assert data.containsKey(key);
        return data.get(key);
    }
    public void merge(ExtraData other){
        for (String key : other.data.keySet()) {
            if (data.containsKey(key)) {
                System.err.println("OverWriting data value "+ key + " during merge.");
            }
            data.put(key, other.data.get(key));
        }
    }
    //Try and find out what an entry meant.
    private void parseEntry(String entry) throws Exception {
        if (entry.contains(":")){
            String[] tmp = entry.split(":", 2);
            String prefix = tmp[0].strip(), rest = tmp[1].strip();
            switch (prefix) {
                // let == data value (bool/int, only int for now)
                case "let" -> parseValue(rest);
                //flags and types
                case "type", "flag" -> data.put(prefix, new DataValue(rest));
                default -> throw new Exception("Unkown prefix encountered: '"+prefix+"'");
            }
        } else {
            data.put(null, new DataValue(entry));
//            throw new Exception("No prefix encoutered in entry '"+entry +"'");
        }
    }
    //Parse a datavalue (e.g. int)
    // in general these look like this: 'weight = int:0'
    private void parseValue(String assignment) throws Exception {
        String[] tmp = assignment.split("=", 2);
        String name = tmp[0].strip(), rest = tmp[1].strip();
        tmp = rest.split(":", 2);
        String type = tmp[0].strip(), value = tmp[1].strip();
        switch (type){
            case "int" -> {
                data.put(name, new DataValue(Integer.parseInt(value)));
            }
            case "boolean" -> {
                data.put(name, new DataValue(Boolean.parseBoolean(value)));
            }
            default -> {
                throw new Exception("I don't know how to handle type '"+type+"'");
            }
        }
    }

    public enum DataValueEnum {
        STRING, INT, BOOL;
    }
    public class DataValue {
        final DataValueEnum type;
        final String stringValue;
        final int intValue;
        final boolean boolValue;

        public DataValue(String stringValue) {
            this.type = DataValueEnum.STRING;
            this.stringValue = stringValue;
            this.intValue = -1;
            this.boolValue = false;
        }
        public DataValue(int intValue) {
            this.type = DataValueEnum.INT;
            this.intValue = intValue;
            this.boolValue = false;
            this.stringValue = null;
        }
        public DataValue(boolean boolValue) {
            this.type = DataValueEnum.BOOL;
            this.boolValue = boolValue;
            this.intValue = -1;
            this.stringValue = null;
        }
    }
}
