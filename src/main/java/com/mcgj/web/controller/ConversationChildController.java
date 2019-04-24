package com.mcgj.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.UserCollectionConversationChild;
import com.mcgj.service.IConversationChildService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * 贴吧子贴控制器
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversationChild")
public class ConversationChildController extends AbstractBaseController{
	
	Logger log = Logger.getLogger(ConversationChildController.class);
	
	@Autowired
	private IConversationChildService conversationChildService;
	
	/**
	 * 根据贴子id查询,根据贴子id查询,获取当前帖子的第一层数据
	 * @return
	 */
	@RequestMapping("/selectConversationChildById")
	@ResponseBody
	public ResultDTO selectConversationChildById(Integer id){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildService.selectConversationChildById(id));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 根据贴吧id查询其下的所有帖子数据
	 * @return
	 */
	@RequestMapping("/selectConversationChildByConversationId")
	@ResponseBody
	public ResultDTO selectConversationChildByConversationId(ConversationChild conversationChild){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildService.selectConversationChildByConversationId(conversationChild));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 新增帖子的方法
	 * @return
	 */
	@RequestMapping("/addConversationChild")
	@ResponseBody
	public ResultDTO addConversationChild(ConversationChild conversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.addConversationChild(conversation);
			result.setResult(conversation.getId());//返回贴子id，爬虫使用
//			result.setResult(con);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	
	/**
	 * 新增贴子收藏方法
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/addConversationChildCollection")
	@ResponseBody
	public ResultDTO addConversationChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.addConversationChildCollection(userCollectionConversationChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_COLLECTION_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_COLLECTION_ERROR);
			return result;
		}
	}
	
	/**
	 * 删除贴子收藏方法
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/deleteConversationChildCollection")
	@ResponseBody
	public ResultDTO deleteConversationChildChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.deleteConversationChildCollection(userCollectionConversationChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_COLLECTION_CANCEL_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_COLLECTION_CANCEL_ERROR);
			return result;
		}
	}
	
	/**
	 * 根据贴子和用户id判断用户是否收藏了该帖
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/selectConversationChildCollection")
	@ResponseBody
	public ResultDTO selectConversationChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			UserCollectionConversationChild uc = conversationChildService.selectConversationChildCollection(userCollectionConversationChild);
			result.setResult(uc);
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
	 * 查询用户所发布的贴子 
	 * @param conversationChild
	 * @return
	 */
	@RequestMapping("/selectUserPublishConversationChild")
	@ResponseBody
	public ResultDTO selectUserPublishConversationChild(ConversationChild conversationChild){
		ResultDTO result = new ResultDTO();
		try{
			Map<String,Object> selectUserPublishConversationChild = conversationChildService.selectUserPublishConversationChild(conversationChild);
			result.setResult(selectUserPublishConversationChild);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setResult(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	
	/**
	 * 查询对应天数内最为活跃的贴吧排名
	 * @param limit 要查询的贴吧数量
	 * @return
	 */
	@RequestMapping("/selectMaxConversationChildByDay")
	@ResponseBody
	public ResultDTO selectMaxConversationChildByDay(Integer day){
		ResultDTO result = new ResultDTO();
		try{
			List<ConversationChild> selectMaxConversationChildByDays = conversationChildService.selectMaxConversationChildByDay(day);
			result.setResult(selectMaxConversationChildByDays);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			return result;
		}
	}
	
	
}
