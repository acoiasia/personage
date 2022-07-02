/**
 * @author 李永强
 * @date 2022/7/2 12:24
 * 统一异常处理器
 */

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo error(Exception e) {
        e.printStackTrace();
        return ResultVo.error(); // 通用异常结果
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultVo error(NullPointerException e) {
        e.printStackTrace();
        return ResultVo.setResult(ResultCodeEnum.NULL_POINT);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResultVo error(IndexOutOfBoundsException e) {
        e.printStackTrace();
        return ResultVo.setResult(ResultCodeEnum.HTTP_CLIENT_ERROR);
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(CMSException.class)
    @ResponseBody
    public ResultVo error(CMSException e) {
        e.printStackTrace();
        return ResultVo.error().message(e.getMessage()).code(e.getCode());
    }
}
