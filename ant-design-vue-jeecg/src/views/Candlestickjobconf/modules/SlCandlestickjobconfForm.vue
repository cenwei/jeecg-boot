<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="任务id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['jobid', validatorRules.jobid]" placeholder="请输入任务id"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="交易所" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['market', validatorRules.market]" :trigger-change="true" dictCode="market_code" placeholder="请选择交易所" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="股票代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['stockcode', validatorRules.stockcode]" placeholder="请输入股票代码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="周期代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['period', validatorRules.period]" :trigger-change="true" dictCode="kcp" placeholder="请选择周期代码" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['beginning', validatorRules.beginning]" placeholder="请输入开始日期"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['ending', validatorRules.ending]" placeholder="请输入截止日期"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="步进最大值" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['step', validatorRules.step]" placeholder="请输入步进最大值" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SlCandlestickjobconfForm',
    components: {
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          jobid: {
            rules: [
              { required: true, message: '请输入任务id!'},
            ]
          },
          market: {
            rules: [
              { required: true, message: '请输入交易所!'},
            ]
          },
          stockcode: {
            rules: [
              { required: true, message: '请输入股票代码!'},
            ]
          },
          period: {
            rules: [
              { required: true, message: '请输入周期代码!'},
            ]
          },
          beginning: {
            initialValue:"200003060930",
            rules: [
              { required: true, message: '请输入开始日期!'},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          ending: {
            rules: [
              { required: true, message: '请输入截止日期!'},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          step: {
            initialValue:2000,
            rules: [
              { required: true, message: '请输入步进最大值!'},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
        },
        url: {
          add: "/candlestickjobconf/slCandlestickjobconf/add",
          edit: "/candlestickjobconf/slCandlestickjobconf/edit",
          queryById: "/candlestickjobconf/slCandlestickjobconf/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'jobid','market','stockcode','period','beginning','ending','step','summary'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'jobid','market','stockcode','period','beginning','ending','step','summary'))
      },
    }
  }
</script>