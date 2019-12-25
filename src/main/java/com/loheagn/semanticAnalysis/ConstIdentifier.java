package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.NumberToBytes;
import org.apache.commons.lang3.ArrayUtils;

public class ConstIdentifier {
    private Object value;
    private TokenType type;

    public ConstIdentifier(Object value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    List<Byte> toBytes() {
        short typeNumber = 0;
        if(type== TokenType.INT) typeNumber = 1;
        else if(type == TokenType.DOUBLE) typeNumber = 2;
        List<Byte> result = new ArrayList<>(NumberToBytes.numberToBytes(typeNumber, 1));
        if(type == TokenType.STRING){
            String string = (String) value;
            result.addAll(NumberToBytes.numberToBytes(string.length(),2));
            result.addAll(Arrays.asList(ArrayUtils.toObject(string.getBytes())));
        } else if(type == TokenType.DOUBLE) {
            result.addAll(NumberToBytes.numberToBytes(((Double)value),8));
        } else{
            result.addAll(NumberToBytes.numberToBytes(((Integer)value),4));
        }
        return result;
    }

    @Override
    public String toString() {
        if (type == TokenType.STRING) {
            return "S " + "\"" + value.toString() + "\"";
        } else if (type == TokenType.DOUBLE) {
            return "D " + "0x" + Long.toHexString(Double.doubleToLongBits((Double) value));
        } else {
            return "I " + "0x" + Long.toHexString((Integer) value);
        }
    }
}
