package com.zhu2chu.all.common.kit;

/**
 * 2017年4月15日 09:28:30
 * 加一些方法来用
 * 
 * @author ThreeX
 *
 */
@SuppressWarnings("unchecked")
public class Ret extends com.jfinal.kit.Ret {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String YES = "yes";
    private static final String NO = "no";

    public Ret setYes() {
        super.put(YES, Boolean.TRUE);
        super.put(NO, Boolean.FALSE);
        return this;
    }

    public Ret setYes(Object key, Object value) {
        super.put(YES, Boolean.TRUE);
        super.put(NO, Boolean.FALSE);
        super.put(key, value);
        return this;
    }

    public Ret setNo() {
        super.put(YES, Boolean.FALSE);
        super.put(NO, Boolean.TRUE);
        return this;
    }

    public Ret setNo(Object key, Object value) {
        super.put(YES, Boolean.FALSE);
        super.put(NO, Boolean.TRUE);
        super.put(key, value);
        return this;
    }

    public static Ret yes() {
        return new Ret().setYes();
    }

    public static Ret yes(Object key, Object value) {
        return new Ret().setYes(key, value);
    }

    public static Ret no() {
        return new Ret().setNo();
    }

    public static Ret no(Object key, Object value) {
        return new Ret().setNo(key, value);
    }

}
