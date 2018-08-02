package com.mumu.core.utils;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/25.
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> {
    public static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try{
            System.out.println("json反序列化 -->" + jsonParser.getText());
            return format.parse(jsonParser.getText());
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
