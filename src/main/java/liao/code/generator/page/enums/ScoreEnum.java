package liao.code.generator.page.enums;

/**
 * Created by ao on 2017/11/8.
 */
public enum ScoreEnum {
    TITLE_WHO(5),
    TITLE_WHAT(5),
    ELE_WHO(2),
    ELE_WHAT(3);

    private int score;

    ScoreEnum(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
