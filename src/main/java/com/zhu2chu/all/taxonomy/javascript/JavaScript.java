package com.zhu2chu.all.taxonomy.javascript;

import java.io.FileNotFoundException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript {

    public static void main(String[] args) {
        executeJs("s");
    }

    public static void executeJs(String paras) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");
        try {
            engine.eval(new java.io.FileReader("data/test.js"));
            Invocable inv = (Invocable)engine; 
            Object a = inv.invokeFunction("test", paras);
            System.out.println(a.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } 
    }

}
