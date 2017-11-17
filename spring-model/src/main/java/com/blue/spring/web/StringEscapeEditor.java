package com.blue.spring.web;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;

public class StringEscapeEditor extends PropertyEditorSupport {
    private boolean escapeHTML;
    private boolean escapeJavaScript;
    private boolean escapeSQL;
    private static Map<String,String> escapeCharMap = new HashMap<String, String>();
    static {
        escapeCharMap.put("<","&lt;");
        escapeCharMap.put(">","&gt;");
        escapeCharMap.put("\\(","&#40;");
        escapeCharMap.put("\\)","&#41;");
        escapeCharMap.put("'","&#39;");
//        escapeCharMap.put("\"","&quot;");
        escapeCharMap.put("eval\\((.*)\\","");
        escapeCharMap.put("eval\\((.*)\\","");
        escapeCharMap.put("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","");
    }
    public StringEscapeEditor() { super(); }
    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            value = escapeValue(value);
            setValue(value);
            }
    }
    @Override
    public String getAsText() { Object value = getValue(); return value
            != null ? value.toString() :"";}

    public String escapeValue(String text){
        if(text == null || text.equals("")){
            return "";
        }
        for(String str:escapeCharMap.keySet()){
            if(text.contains(str)){
                text = text.replace(str,escapeCharMap.get(str));
                continue;
            }
        }
        return text;
    }
}