package cn.com.code.common.bean;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 11:25 下午
 */

public enum  Constants {

    /**
     * 删除
     */
    IS_DELETE_YES("1"),
    /**
     * 没删除
     */
    IS_DELETE_NO("0");









    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
