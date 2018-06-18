package crawler;

import com.google.common.base.Joiner;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> listStr) {
        if (listStr == null || listStr.isEmpty()) return null ;

        return Joiner.on(",")
                .skipNulls()
                .join(listStr);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if ( dbData == null )
            return new ArrayList<>() ;

        String[] sizesInShort = dbData.split(",") ;
        return Arrays.asList(sizesInShort) ;
    }
}
