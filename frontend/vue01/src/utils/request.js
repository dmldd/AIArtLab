import axios from 'axios'
// import router from '@/router'
import ElementUI from 'element-ui';

const request = axios.create({
	baseURL: 'http://localhost:7651',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 300000    //5分钟超时
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    if (!config.headers['Content-Type']) {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
      }
    
    let token=localStorage.getItem("token");
    config.headers['Authorization'] = token;  // 设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        //如果token失效，或者没携带token被后端拦截下来，那么code为401，此时返回null
        if(res.code===401){
            ElementUI.Message({
                message:res.message,
                type:'error'
         });
            return null
        }
        else if(res.code==402){
            ElementUI.Message({
                    message:res.message,
                    type:'error'
             });
             return null
        }
        return res;
 
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default request

