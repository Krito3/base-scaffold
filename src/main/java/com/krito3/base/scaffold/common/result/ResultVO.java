/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krito3.base.scaffold.common.result;

import com.krito3.base.scaffold.common.result.IResultCode;
import com.krito3.base.scaffold.common.result.ResultCode;
import com.krito3.base.scaffold.common.utils.BaseConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;


/**
 * 统一API响应结果封装
 *
 * @author Chill
 */
@Data
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码", required = true)
    private int code;
    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;
    @ApiModelProperty(value = "承载数据")
    private T data;
    @ApiModelProperty(value = "返回消息", required = true)
    private String message;

    private ResultVO(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private ResultVO(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private ResultVO(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private ResultVO(IResultCode resultCode, T data, String message) {
        this(resultCode.getCode(), data, message);
    }

    private ResultVO(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = ResultCode.SUCCESS.code == code;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable ResultVO<?> result) {
        return Optional.ofNullable(result)
                .map(x -> ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable ResultVO<?> result) {
        return !ResultVO.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> data(T data) {
        return data(data, BaseConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> data(int code, T data, String msg) {
        return new ResultVO<>(code, data, data == null ? BaseConstant.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> success(String msg) {
        return new ResultVO<>(ResultCode.SUCCESS, msg);
    }

    public static <T> ResultVO<T> success() {
        return new ResultVO<>(ResultCode.SUCCESS);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> success(IResultCode resultCode) {
        return new ResultVO<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> success(IResultCode resultCode, String msg) {
        return new ResultVO<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> fail(String msg) {
        return new ResultVO<>(ResultCode.FAILURE, msg);
    }


    /**
     * 返回R
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> fail(int code, String msg) {
        return new ResultVO<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> fail(IResultCode resultCode) {
        return new ResultVO<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ResultVO<T> fail(IResultCode resultCode, String msg) {
        return new ResultVO<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @return R
     */
    public static <T> ResultVO<T> status(boolean flag) {
        return flag ? success(BaseConstant.DEFAULT_SUCCESS_MESSAGE) : fail(BaseConstant.DEFAULT_FAILURE_MESSAGE);
    }

}
