package com.hukuton.longoidotulunkristianpcs.model;

import com.hukuton.longoidotulunkristianpcs.enums.Type;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class IntentData {
    private int count;
    private int position;
    private Type type;

    public IntentData(Type type, int count, int position) {
        this.type = type;
        this.count = count;
        this.position = position;
    }

    /**
     *
     * @return count Size of Longoi, Hoturan Sumambayang, Zabur, Pomintaan Dua or Ongovokon
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return position of of clicked item on RecyclerView of Longoi, Hoturan Sumambayang, Zabur, Pomintaan Dua or Ongovokon
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @return type enum of Longoi, Hoturan Sumambayang, Zabur, Pomintaan Dua or Ongovokon
     */
    public Type getType() {
        return type;
    }
}
