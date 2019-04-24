package com.mcgj.web.controller;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.Dict;
import com.mcgj.service.IDictService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * 字典控制器
 * @author 杨晨
 *
 */
@Controller
@RequestMapping("/dict")
public class DictController extends AbstractBaseController{
	
	Logger log = Logger.getLogger(DictController.class);
	
	@Autowired
	private IDictService dictService;
	/**
	 * 根据传入的代码值查询
	 * @return
	 */
	@RequestMapping("/selectDictByCode")
	@ResponseBody
	public ResultDTO selectDictByCode(HttpServletRequest request){
		ResultDTO result = new ResultDTO(); 
		try{
			String codeValue = request.getParameter("codeValue");
			List<Dict> dicts = dictService.selectDictByCode(codeValue);
			result.setResult(dicts);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			result.setResult(e.getMessage());
			return result;
		}
	}
}
