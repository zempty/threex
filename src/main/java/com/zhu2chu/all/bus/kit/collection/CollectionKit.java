package com.zhu2chu.all.bus.kit.collection;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 2018年1月10日 12:15:40<br>
 * 集合操作类
 * 记录自己认为用到的方法
 * 
 * @author ThreeX
 *
 */
public class CollectionKit {

    /**
     * 移除某些字段为空的元素
     * @param data
     * @param fields
     * @return
     */
    public static void removeFieldNull(List<Record> data, String... fields) {
        if (data==null || data.size()==0) {
            return;
        }

        for (int z=0; z<data.size(); z++) {
            Record r = data.get(z);
            boolean canRemove = false;
            for (int i=0; i<fields.length; i++) {
                Object a = r.get(fields[i]);
                if (a == null) {
                    canRemove = true;
                    break;
                }
            }

            if (canRemove) {
                data.remove(z);
                z--;
            }
        }

    }

}
