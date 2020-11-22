package com.seungchan.distributemoney_v2.common.util.properties;

public class PropertiesParameterModel {

    private String key;
    private String value;

    public PropertiesParameterModel() {
    }

    public PropertiesParameterModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private PropertiesParameterModel(Builder builder) {
        setKey(builder.key);
        setValue(builder.value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(PropertiesParameterModel copy) {
        Builder builder = new Builder();
        builder.key = copy.getKey();
        builder.value = copy.getValue();
        return builder;
    }

    public static final class Builder {
        private String key;
        private String value;

        private Builder() {
        }

        public Builder withKey(String val) {
            key = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public PropertiesParameterModel build() {
            return new PropertiesParameterModel(this);
        }
    }

    @Override
    public String toString() {
        return "PropertiesParameterModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
