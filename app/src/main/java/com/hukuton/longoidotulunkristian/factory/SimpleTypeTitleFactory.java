package com.hukuton.longoidotulunkristian.factory;

import com.hukuton.longoidotulunkristian.ListValues;
import com.hukuton.longoidotulunkristian.enums.Type;
import com.hukuton.longoidotulunkristian.model.ActionBarTitle;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class SimpleTypeTitleFactory {

    public ActionBarTitle getInstantTitleAndSubtitle(Type type, int position){
        ActionBarTitle actionBarTitle = null;

        if(type == Type.LONGOI)
            actionBarTitle = new ActionBarTitle("Longoi", ListValues.LONGOI[position]);
        else if(type == Type.HOTURAN_SUMAMBAYANG)
            actionBarTitle = new ActionBarTitle("Hoturan Sumambayang", ListValues.HOTURAN_SUMAMBAYANG[position]);
        else if(type == Type.ZABUR)
            actionBarTitle = new ActionBarTitle("Zabur", ListValues.ZABUR[position]);
        else if(type == Type.ONGOVOKON)
            actionBarTitle = new ActionBarTitle("Ongovokon", ListValues.ONGOVOKON[position]);
        else if(type == Type.POMINTAAN_DUA)
            actionBarTitle = new ActionBarTitle("Pomintaan Dua", ListValues.POMINTAAN_DUA[position]);

        return actionBarTitle;
    }

}
