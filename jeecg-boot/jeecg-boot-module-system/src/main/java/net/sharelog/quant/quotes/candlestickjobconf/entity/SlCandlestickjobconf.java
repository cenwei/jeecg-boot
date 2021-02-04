package net.sharelog.quant.quotes.candlestickjobconf.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 获取K线数据任务配置
 * @Author: jeecg-boot
 * @Date:   2021-02-03
 * @Version: V1.0
 */
@Data
@TableName("sl_candlestickjobconf")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sl_candlestickjobconf对象", description="获取K线数据任务配置")
public class SlCandlestickjobconf implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**任务id*/
	@Excel(name = "任务id", width = 15)
    @ApiModelProperty(value = "任务id")
    private java.lang.String jobid;
	/**交易所*/
	@Excel(name = "交易所", width = 15, dicCode = "market_code")
	@Dict(dicCode = "market_code")
    @ApiModelProperty(value = "交易所")
    private java.lang.String market;
	/**股票代码*/
	@Excel(name = "股票代码", width = 15)
    @ApiModelProperty(value = "股票代码")
    private java.lang.String stockcode;
	/**周期代码*/
	@Excel(name = "周期代码", width = 15, dicCode = "kcp")
	@Dict(dicCode = "kcp")
    @ApiModelProperty(value = "周期代码")
    private java.lang.String period;
	/**开始日期*/
	@Excel(name = "开始日期", width = 15)
    @ApiModelProperty(value = "开始日期")
    private java.lang.String beginning;
	/**截止日期*/
	@Excel(name = "截止日期", width = 15)
    @ApiModelProperty(value = "截止日期")
    private java.lang.String ending;
	/**步进最大值*/
	@Excel(name = "步进最大值", width = 15)
    @ApiModelProperty(value = "步进最大值")
    private java.lang.Integer step;
	/**最后获取总数*/
	@Excel(name = "最后获取总数", width = 15)
    @ApiModelProperty(value = "最后获取总数")
    private java.lang.Integer summary;
}
