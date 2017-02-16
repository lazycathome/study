package cn.bigdb.validation.mvc;

import javax.validation.Valid;

public class MvcController {

	/** 
     * 这里的@Valid必须书写, bindingResult参数也必须书写在后面,否则验证不通过就会返回400 
     * @param entity 
     * @param result 
     * @return 
     */  
    /*@RequestMapping(value="/valid")  
    public String validator(@Valid Entity entity,BindingResult result){  
        if(result.hasErrors()){  
            //如果严重没有通过,跳转提示  
            return "error";  
        }else{  
            //继续业务逻辑  
        }  
        return "success";  
    }  */
}
