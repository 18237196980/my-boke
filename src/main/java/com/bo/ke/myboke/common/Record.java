package com.bo.ke.myboke.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

@JsonSerialize(
        using = RecordSerializer.class
)
public class Record implements Serializable {

    private Map<String, Object> fields = new HashMap();
    private String recordName = "";
    private String primaryKeyName = "";

    public Record() {
    }

    public Record(String recordName) {
        this.recordName = recordName;
    }

    public Record(String recordName, String primaryKeyName) {
        this.recordName = recordName;
        this.primaryKeyName = primaryKeyName;
    }

    public static Record build() {
        return new Record();
    }

    public static Record build(String recordName) {
        return new Record(recordName);
    }

    public static Record build(String recordName, String primaryKeyName) {
        return new Record(recordName, primaryKeyName);
    }

    public Record set(String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            return this;
        } else {
            this.fields.put(key, value);
            return this;
        }
    }

    public Record set(String key, Object value, boolean condition) {
        return condition ? this.set(key, value) : this;
    }

    public Record set(String key, Object value, BooleanSupplier supplier) {
        return supplier.getAsBoolean() ? this.set(key, value) : this;
    }

    public Map<String, Object> toMap() {
        return this.fields;
    }

}
