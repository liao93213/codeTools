package liao.code.generator.page.util;

import liao.code.generator.page.enums.ScoreEnum;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.TwoTuple;

import java.util.*;

/**
 * Created by ao on 2017/10/17.
 */
public class ColNameSearchTools {
    private static final int minLength = 3;
    public Column getMatchCol(List<Table> tableList,String eleName){
        TreeSet<Result> tree = new TreeSet<>();
        for(int i = 0;i < tableList.size(); i++){
            Table table = tableList.get(i);
            tree.addAll(getMatchCol(table,eleName,i));
        }
        Result result = notEmptyGetFirst(tree);
        return result == null ? null : result.column;
    }
    public TreeSet<Result> getMatchCol(Table table,String eleName,int i){
        TreeSet<Result> matchCol = pageMatchDB(table,eleName,i);
        return matchCol;
    }
    private TreeSet<Result> pageMatchDB(Table table,String eleName,int tableIndex){

        TreeSet<Result> tree = new TreeSet<Result>();
        tree.addAll(orderMatch(table,eleName,tableIndex));
        tree.addAll(reverseMatch(table, eleName, tableIndex));
        tree.addAll(middleMatch(table, eleName, tableIndex));
        tree.addAll(wordByWordMatch(table,eleName, tableIndex));
        return tree;
    }
    private void ifNotNullAddTree(Result result,TreeSet<Result> tree){
        if(result != null) {
            tree.add(result);
        }
    }
    private Result notEmptyGetFirst(TreeSet<Result> tree){
        return tree == null || tree.isEmpty() ? null : tree.first();
    }

    private TreeSet<Result> orderMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength > eleName.length() ? eleName.length() : minLength;
        boolean nextTable = false;
        for(int i = eleName.length(); i >= minLen;i--){
            String matchStr = eleName.substring(0,i);
            for(Column col : columnList){
                String comment = col.getComment();
                boolean isMatch =  comment.contains(matchStr);
                if(!nextTable){
                    nextTable = isMatch;
                }
                if(isMatch) {
                    matchColSet.add(new Result(matchStr.length(), 1, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
                }
            }
            if(nextTable){
                break;
            }
        }
        return matchColSet;
    }

    public TreeSet<Result> middleMatch(Table table,String eleName,int tableIndex){
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength > eleName.length() ? eleName.length() : minLength;
        int endIndex = 1;
        for (int startIndex = 2; startIndex < eleName.length() - minLen; ) {
            String matchStr = eleName.substring(startIndex, eleName.length() - endIndex);
            endIndex++;
            if (matchStr.length() < minLen) {
                endIndex = 1;
                startIndex++;
                continue;
            }
            TreeSet<Result> matchCol = strMatchColName(matchStr, eleName, table, tableIndex, 3);
            matchColSet.addAll(matchCol);
            if (!matchCol.isEmpty()) {
                break;
            }


        }
        return matchColSet;
    }
    private TreeSet<Result> strMatchColName(String matchStr,String eleName,Table table,int tableIndex,int type){
        TreeSet<Result> matchColSet = new TreeSet<>();
        for(Column col : table.getColumnList()){
            String comment = col.getComment();
            boolean isMatch =  comment.contains(matchStr);
            if(isMatch) {
                matchColSet.add(new Result(matchStr.length(), type, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
            }
        }
        return matchColSet;
    }
    private TreeSet<Result> reverseMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen =minLength > eleName.length() ? 1 : eleName.length() - minLength+1;
        boolean nextTable = false;
        for(int i = 1; i <= minLen;i++){
            String matchStr = eleName.substring(i-1,eleName.length());
            for(Column col : columnList){
                String comment = col.getComment();
                boolean isMatch =  comment.contains(matchStr);
                if(!nextTable){
                    nextTable = isMatch;
                }
                if(isMatch) {
                    matchColSet.add(new Result(matchStr.length(), 3, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
                }
            }
            if(nextTable){
                break;
            }
        }
        return matchColSet;
    }
    private Column dbMatchPage(List<Column> columnList,String eleName){
        return null;
    }

    private TreeSet<Result> wordByWordMatch(Table table,String eleName,int index){
        if(eleName.length() < 2){
            return new TreeSet<>();
        }
        TreeSet<Result> matchColSet = new TreeSet<>();
        for(Column col : table.getColumnList()) {
            int matchCount = 0;
            for (int i = 2; i < eleName.length(); i=i+2) {
                if(col.getComment().contains(eleName.substring(i-2,i))){
                    matchCount+=2;
                }
            }
            if(matchCount > 2)
                //减1是为了降低优先级
                matchColSet.add(new Result(matchCount-1, 3, col, table, true, Math.abs(eleName.length() - col.getComment().length()), index));
        }
        return matchColSet;
    }

    /**
     * 发现名称前缀部分，提高匹配精度
     * @return
     */
    private String findNamePre(){
        return  "";
    }

    /**
     * 根据上下文进行匹配
     * @return
     */
    public List<Column> contextMatch(List<Table> tableList,String eleName,String title){
        TreeMap<Integer, TreeSet<Result>> map = new TreeMap<>();
        for(int index = 0;index < tableList.size(); index++){
            Table table = tableList.get(index);
            String[] comments = table.getComment().split("-");
            int score = getScore(comments, title, eleName);
            TreeSet<Result> tree = new TreeSet<>();
            if(map.get(score) != null) {
                tree.addAll(map.get(score));//同分时筛选
            }

            tree.addAll(getMatchCol(table, eleName, index));//完全匹配

            for (String comment : comments) {
               /* Result result = getMatchCol(table, eleName.replace(comment, ""), index);
                if(result != null)
                    result.matchLength += comment.length();*/
                tree.addAll(getMatchCol(table, eleName.replace(comment, ""), index));//移除前最匹配
                tree.addAll(wordByWordMatch(table,eleName.replace(comment, ""), index));
            }
            map.put(score,tree);
        }
        LinkedList<Column> columnList = new LinkedList<>();
        for(Map.Entry<Integer,TreeSet<Result>> entry : map.entrySet()){
            for(Result r : entry.getValue()){
                if(!columnList.contains(r.column)) {
                    columnList.add(r.column);
                }
            }
        }
        return columnList;
    }

    private int getScore(String[] comments,String title,String eleName){
        int score = 0;
        String who = comments[1];
        String what = comments[0];
        if(comments.length > 1){
            if(title.contains(who)){
                score += ScoreEnum.TITLE_WHO.getScore();
            }
            if(title.contains(what)){
                score += ScoreEnum.ELE_WHO.getScore();
            }
        }
        if(title.contains(what)){
            score += ScoreEnum.TITLE_WHAT.getScore();
        }
        if(eleName.contains(what)){
            score += ScoreEnum.ELE_WHAT.getScore();
        }

        return score;
    }

    private static class Result implements Comparable<Result>{
        int matchLength;//字符相匹配的长度
        int matchOrder;//匹配顺序
        Column column;
        private Table table;
        boolean isPageMatchDB;//是否页面匹配字段名匹配数据库成功
        int lengthDiff;//相匹配的字符串字符数差
        int tableIndex;//第几张表匹配
        public Result(int matchLength,
                int matchOrder,
                Column column, Table table,
                boolean isPageMatchDB,
                int lengthDiff,
                int tableIndex){
            this.column = column;
            this.table = table;
            this.matchLength = matchLength;
            this.matchOrder = matchOrder; //正序匹配
            this.isPageMatchDB = isPageMatchDB;
            this.lengthDiff = lengthDiff;
            this.tableIndex = tableIndex;
        }

        @Override
        public int compareTo(Result o) {
            if(o.matchLength > this.matchLength){//字符相匹配的长度
                return 1;
            }else if(o.matchLength == this.matchLength){
                if(o.lengthDiff > this.lengthDiff ){//相匹配的字符串字符数差，差距越小匹配度越高
                    return -1;
                }else if(lengthDiff < this.lengthDiff){
                    return 1;
                }else {
                    if(this.tableIndex == o.tableIndex) {//第几张表匹配
                        if (this.matchOrder == o.matchOrder) {//正序匹配
                            if (this.isPageMatchDB) {//是否页面匹配字段名匹配数据库成功
                                return 1;
                            } else {
                                return -1;
                            }
                        } else if (this.matchOrder < o.matchOrder) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }else if(this.tableIndex < o.tableIndex){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            }else{
                return -1;
            }
        }
    }
}
