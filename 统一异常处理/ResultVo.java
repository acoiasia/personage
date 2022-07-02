import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李永强
 * @date 2022/7/2 12:15
 * 统一结果类
 */
@Data
public class ResultVo {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    // 构造器私有
    private ResultVo(){}

    // 通用返回成功
    public static ResultVo ok() {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        resultVo.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultVo.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return resultVo;
    }

    // 通用返回失败，未知错误
    public static ResultVo error() {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        resultVo.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        resultVo.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return resultVo;
    }

    // 设置结果，形参为结果枚举
    public static ResultVo setResult(ResultCodeEnum result) {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(result.getSuccess());
        resultVo.setCode(result.getCode());
        resultVo.setMessage(result.getMessage());
        return resultVo;
    }

    /**------------使用链式编程，返回类本身-----------**/

    // 自定义返回数据
    public ResultVo data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public ResultVo data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public ResultVo message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public ResultVo code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public ResultVo success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
