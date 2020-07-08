package liao.testdata.create;

import liao.testdata.create.model.Columns;
import liao.utils.enums.WhetherEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2020/4/9.
 */
public class SearchElementUtils {
    private static String tablePre = "t_";
    public static void searchColumn(Columns columns,List<Columns> columnsList){
        for(Columns toColumn : columnsList){
            if(columns.getColumnName().equalsIgnoreCase(toColumn.getColumnName())){
                toColumn.addScore(100);
            }
            if(columns.getComment().equals(toColumn.getColumnName())){
                toColumn.addScore(10);
            }
            if(columns.isId()){
                if(toColumn.getColumnName().equalsIgnoreCase("id")){
                    String idName = toColumn.getTableName().replaceAll(tablePre,"")+"id";
                    if(columns.getColumnName().equalsIgnoreCase(idName)){
                        toColumn.addScore(95);
                    }
                }
                if(toColumn.getIsPrimary() == WhetherEnum.YES.getValue()){
                    toColumn.addScore(100);
                }
            }else{

            }

        }

    }


}
