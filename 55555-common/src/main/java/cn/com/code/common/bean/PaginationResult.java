package cn.com.code.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxp
 *
 * @version 创建时间：2018年8月22日
 *
 * @declare 分页响应结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "PaginationResult", description = "标准自定义响应结构", parent = StandardResult.class)
public class PaginationResult<T> extends StandardResult {
	
	@ApiModelProperty(value = "总数据量")
    private int total;  // 总条数
	
	@ApiModelProperty(value = "总页数")
	private int pages;	// 总页数

	@ApiModelProperty(value = "对象")
	private List<T> data;
    


	/**
	 * 响应
	 * @param state
	 * @param msg
	 * @param data
	 * @param code
	 * @param total
	 * @param pages
	 */
	public PaginationResult(boolean state, String msg, List<T> data, int code, int total, int pages) {
		this.total = total;
		this.pages = pages;
		setCode(code);
		setMsg(msg);
		setState(state);
		setData(data);
	}



	public PaginationResult() {
		
	}

	/**
	 *  响应
	 * @param msg
	 * @param data
	 * @param total
	 * @param pages
	 * @param <T>
	 * @return
	 */
    public static <T> PaginationResult ok(String msg, List<T> data, int total, int pages) {
    	if(data == null) {
    		data = new ArrayList<>();
    	}

    	if(msg == null) {
    		msg = "";
    	}

    	return new PaginationResult(true, msg, data, HttpStatus.OK.code(), total, pages);
    }

	/**
	 * 成功
	 * @param data
	 * @param total
	 * @param pages
	 * @param <T>
	 * @return
	 */
    public static <T> PaginationResult ok(List<T> data, int total, int pages) {
    	
    	return PaginationResult.ok(null, data, total, pages);
    }

	/**
	 * 成功
	 * @return
	 */
	public static PaginationResult ok() {
    	return PaginationResult.ok(new ArrayList<>(), 0, 0);
    }




}
