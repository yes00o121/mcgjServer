package com.mcgj.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.aop.Log;
import com.mcgj.aop.OperationType;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.service.IConversationChildChildService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * 贴吧楼层控制器
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversationChildChild")
public class ConversationChildChildController {
	
	Logger log = Logger.getLogger(ConversationChildChildController.class);
	
	@Autowired
	private IConversationChildChildService conversationChildChildService;
	
	/**
	 * 根据贴子id查询其下的楼层数据
	 * @return
	 */
	@RequestMapping("/selectCCCByCCId")
	@ResponseBody
	public ResultDTO selectCCCByCCId(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			Map<String,Object> maps = conversationChildChildService.selectCCCByCCId(conversationChildChild);
			result.setResult(maps);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			return result;
		}
	}
	
	/**
	 * 新增楼层方法,新增楼层时消息实时推送给楼主，并且写入回复表中，如果用户不在线则不进行推送
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@Log(OperationType.ADD)
	@RequestMapping("/addConversationChildChild")
	@ResponseBody
	public ResultDTO addConversationChildChild(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildChildService.addConversationChildChild(conversationChildChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_INSERT_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_INSERT_FAILED);
			return result;
		}
	}
	
	/**
	 * 查询用户为查看的所有回复的楼层数据
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/selectConversationChildChildReplyByUserId")
	@ResponseBody
	public ResultDTO selectConversationChildChildReplyByUserId(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildChildService.selectConversationChildChildReplyByUserId(conversationChildChild));
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			return result;
		}
	}
	
	/**
	 * 将该用户所有的未查看楼层都修改为查看状态
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/updateConversationChildChildReplyByUerId")
	@ResponseBody
	public ResultDTO updateConversationChildChildReplyByUerId(HttpServletRequest request,HttpServletResponse response,Integer userId){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildChildService.updateConversationChildChildReplyByUerId(userId);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_EDIT_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_EDIT_FATLED);
			return result;
		}
	}
}
