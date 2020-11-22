package com.seungchan.distributemoney_v2.common.util.properties;

import java.util.*;
import java.util.Map.Entry;

public class PropertiesUtil {
    public static final char parameterSeparator = "\n".charAt(0);
    public static final char keyValueSeparator = "=".charAt(0);
    public static final char commentStart = "#".charAt(0);

    /**
     * 모델 -> 모델
     * @param parameterList
     * @return
     */
    public static PropertiesModel toPropertiesModel(List<PropertiesParameterModel> parameterList) {
        return new PropertiesModel(parameterList);
    }

    /**
     * 모델 -> String
     * @param parameterList
     * @return
     */
    public static String toPropertiesString(List<PropertiesParameterModel> parameterList) {
        StringBuilder sb = new StringBuilder();
        if (parameterList != null) {
            for (int i = 0; i < parameterList.size(); i++) {
                PropertiesParameterModel parameter = parameterList.get(i);
                sb.append(parameter.getKey())
                        .append(keyValueSeparator)
                        .append(parameter.getValue());
                if (i < parameterList.size() - 1)
                    sb.append(parameterSeparator);
            }
        }
        return sb.toString();
    }

    /**
     * 모델 -> String
     * @param propertiesModel
     * @return
     */
    public static String toPropertiesString(PropertiesModel propertiesModel) {
        if (propertiesModel == null)
            return null;
        return toPropertiesString(propertiesModel.getParameterList());
    }

    /**
     * 모델 -> Map
     * @param parameterList
     * @return
     */
    public static Map<String, Object> toMap(List<PropertiesParameterModel> parameterList) {
        Map<String, Object> map = new HashMap<>();
        if (parameterList != null) {
            for (PropertiesParameterModel parameter : parameterList) {
                map.put(parameter.getKey(), parameter.getValue());
            }
        }
        return map;
    }

    /**
     * 모델 -> Map
     * @param queryStringModel
     * @return
     */
    public static Map<String, Object> toMap(PropertiesModel queryStringModel) {
        if (queryStringModel == null)
            return null;
        return toMap(queryStringModel.getParameterList());
    }

    /**
     * Map -> 모델
     * @param map
     * @return
     * @throws Exception
     */
    public static List<PropertiesParameterModel> toPropertiesParameterModelList(Map<String, Object> map) throws Exception {
        List<PropertiesParameterModel> parameterList = new ArrayList<>();
        if (map != null) {
            PropertiesParameterModel parameter = new PropertiesParameterModel();
            for (Entry<String, Object> entry : map.entrySet()) {
                if (parameter.getKey() == null)
                    parameter.setKey(entry.getKey());
                if (parameter.getValue() == null)
                    parameter.setValue(String.valueOf(entry.getValue()));
            }
        }
        return parameterList;
    }

    /**
     * Map -> 모델
     * @param map
     * @return
     * @throws Exception
     */
    public static PropertiesModel toPropertiesModel(Map<String, Object> map) throws Exception {
        return new PropertiesModel(toPropertiesParameterModelList(map));
    }



    /**
     * Map -> String
     * @param map
     * @return
     */
    public static String toPropertiesString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            int i = 0;
            for (Entry<String, Object> entry : map.entrySet()) {
                sb.append(entry.getKey())
                        .append(keyValueSeparator)
                        .append(entry.getValue());
                if (i < map.size() - 1)
                    sb.append(parameterSeparator);
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * String -> Map
     * @param propertiesString
     * @return
     */
    public static Map<String, Object> toMap(String propertiesString) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringTokenizer propertiesStringTokenizer = new StringTokenizer(propertiesString, Character.toString(parameterSeparator));
        while (propertiesStringTokenizer.hasMoreTokens()) {
            String propertiesStringToken = propertiesStringTokenizer.nextToken();
            propertiesStringToken = trimComment(propertiesStringToken);
            if (propertiesStringToken == null || propertiesStringToken.isBlank())
                continue;

            // 파라미터
            String key = null;
            String value = null;

            StringTokenizer parameterTokenizer = new StringTokenizer(propertiesStringToken, Character.toString(keyValueSeparator));
            int idxParameter = 0; // 파라미터는 key 1개 또는 key=value 2개만 있어야
            while (parameterTokenizer.hasMoreTokens()) {
                idxParameter++;
                String parameterToken = parameterTokenizer.nextToken();
                if (key == null)
                    key = parameterToken;
                else if (value == null) {
                    value = parameterToken;
                }
            }

            // 파라미터 검증
            if (idxParameter != 1 && idxParameter != 2)
                throw new Exception("PropertiesParameter 파라미터 입력이 잘못되었습니다!! idxParameter=" + idxParameter + " (1 or 2 required)");

            map.put(key, value);
        }

        return map;
    }

    /**
     * String -> 모델
     * @param propertiesString
     * @return
     * @throws Exception
     */
    public static List<PropertiesParameterModel> toPropertiesParameterModelList(String propertiesString) throws Exception {
        List<PropertiesParameterModel> parameterList = new ArrayList<>();
        StringTokenizer propertiesStringTokenizer = new StringTokenizer(propertiesString, Character.toString(parameterSeparator));
        while (propertiesStringTokenizer.hasMoreTokens()) {
            String propertiesStringToken = propertiesStringTokenizer.nextToken();
            propertiesStringToken = trimComment(propertiesStringToken);
            if (propertiesStringToken == null || propertiesStringToken.isBlank())
                continue;

            // 파라미터
            PropertiesParameterModel parameter = new PropertiesParameterModel();

            StringTokenizer parameterTokenizer = new StringTokenizer(propertiesStringToken, Character.toString(keyValueSeparator));
            int idxParameter = 0; // 파라미터는 key 1개 또는 key=value 2개만 있어야
            while (parameterTokenizer.hasMoreTokens()) {
                idxParameter++;
                String parameterToken = parameterTokenizer.nextToken();
                if (parameter.getKey() == null)
                    parameter.setKey(parameterToken);
                else if (parameter.getValue() == null)
                    parameter.setValue(parameterToken);
            }

            // 파라미터 검증
            if (idxParameter != 1 && idxParameter != 2)
                throw new Exception("PropertiesParameter 파라미터 입력이 잘못되었습니다!! idxParameter=" + idxParameter + " (1 or 2 required)");

            parameterList.add(parameter);
        }

        return parameterList;
    }

    /**
     * String -> 모델
     * @param propertiesString
     * @return
     * @throws Exception
     */
    public static PropertiesModel toPropertiesModel(String propertiesString) throws Exception {
        return new PropertiesModel(toPropertiesParameterModelList(propertiesString));
    }

    /**
     * 주석 제거
     * @param line
     * @return
     */
    public static String trimComment(String line) {
        if (line == null || line.isBlank())
            return null;
        int idxCommentStart = line.indexOf(commentStart);
        if (idxCommentStart == 0)
            return null;
        if (idxCommentStart == -1)
            return line;

        return line.substring(0, idxCommentStart);
    }
}