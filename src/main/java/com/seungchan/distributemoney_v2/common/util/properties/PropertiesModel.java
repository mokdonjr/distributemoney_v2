package com.seungchan.distributemoney_v2.common.util.properties;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesModel {

    private String propertiesString;
    private List<PropertiesParameterModel> parameterList;
    private Map<String, Object> parameterMap;
    private boolean caseSensitive = true;

    public PropertiesModel(String propertiesString) throws Exception {
        this.propertiesString = propertiesString;
        this.parameterList = PropertiesUtil.toPropertiesParameterModelList(propertiesString);
        this.parameterMap = PropertiesUtil.toMap(propertiesString);
    }

    public PropertiesModel(List<PropertiesParameterModel> parameterList) {
        this.parameterList = parameterList;
        this.parameterMap = PropertiesUtil.toMap(parameterList);
        this.propertiesString = PropertiesUtil.toPropertiesString(parameterList);
    }

    public PropertiesModel(Map<String, Object> parameterMap) throws Exception {
        this.parameterMap = parameterMap;
        this.propertiesString = PropertiesUtil.toPropertiesString(parameterMap);
        this.parameterList = PropertiesUtil.toPropertiesParameterModelList(parameterMap);
    }

    public String getPropertiesString() {
        return propertiesString;
    }

    public void setPropertiesString(String propertiesString) {
        this.propertiesString = propertiesString;
    }

    public List<PropertiesParameterModel> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<PropertiesParameterModel> parameterList) {
        this.parameterList = parameterList;
    }

    public void addParameter(PropertiesParameterModel parameter) {
        addParameter(parameter, false);
    }

    private void addParameter(PropertiesParameterModel parameter, boolean callByPutParameter) {
        if (this.parameterList == null)
            this.parameterList = new ArrayList<>();
        this.parameterList.add(parameter);
        if (!callByPutParameter)
            putParameter(parameter.getKey(), parameter.getValue(), true);
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void putParameter(String key, Object value) {
        putParameter(key, value, false);
    }

    private void putParameter(String key, Object value, boolean callByAddParameter) {
        if (this.parameterMap == null)
            this.parameterMap = new HashMap<>();
        this.parameterMap.put(key, value);
        if (!callByAddParameter)
            addParameter(new PropertiesParameterModel(key, String.valueOf(value)), true);
    }

    public Object getParameter(String key) {
        // 대소문자 구분 없는 체크를 위한 래핑
        Map<String, Object> wrappedParameterMap = caseSensitive ? parameterMap : new CaseInsensitiveMap<>(parameterMap);
        return wrappedParameterMap.get(key);
    }

    public boolean getIsCaseSensitive() {
        return caseSensitive;
    }

    public void setIsCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean contains(String ... keys) {
        // 대소문자 구분 없는 체크를 위한 래핑
        Map<String, Object> wrappedParameterMap = caseSensitive ? parameterMap : new CaseInsensitiveMap<>(parameterMap);
        for (var k : keys) {
            if (!wrappedParameterMap.containsKey(k))
                return false;
        }
        return true;
    }

    public boolean containsOnly(String ... keys) {
        if (parameterMap.size() != keys.length)
            return false;

        // 대소문자 구분 없는 체크를 위한 래핑
        Map<String, Object> wrappedParameterMap = caseSensitive ? parameterMap : new CaseInsensitiveMap<>(parameterMap);
        for (var k : keys) {
            if (!wrappedParameterMap.containsKey(k))
                return false;
        }
        return true;
    }
}