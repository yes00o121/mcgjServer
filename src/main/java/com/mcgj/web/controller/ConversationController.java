package com.mcgj.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.Conversation;
import com.mcgj.entity.User;
import com.mcgj.entity.UserFollowConversation;
import com.mcgj.service.IConversationService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * 贴吧控制器，负责贴吧的数据传输
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversation")
public class ConversationController extends AbstractBaseController{
		
	Logger log = Logger.getLogger(ConversationController.class);
	
	@Autowired
	private IConversationService conversationService;
	
	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 * @return
	 */
	@RequestMapping("/selectNewestConversation")
	@ResponseBody
	public ResultDTO selectNewestConversation(){
		ResultDTO result = new ResultDTO();
		try{
			List<Map<String,Object>> conversations = conversationService.selectNewestConversation();
			result.setResult(conversations);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setSuccess(false);
			return result;
		}
	}
	/**
	 * 添加新的贴吧数据
	 * @return
	 */
	@RequestMapping("/addConversation")
	@ResponseBody
	public ResultDTO addConversation(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.addConversation(conversation);
			result.setMessage(MessageUtil.MSG_INSERT_SUCCESS);
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
	 * 修改贴吧数据
	 * @return
	 */
	@RequestMapping("/updateConversation")
	@ResponseBody
	public ResultDTO updateConversation(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.updateConversation(conversation);
			result.setMessage(MessageUtil.MSG_EDIT_SUCCESS);
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
	 * 查询当前帖子的标题头像等内容
	 * @return
	 */
	@RequestMapping("/selectConversationById")
	@ResponseBody
	public ResultDTO selectConversationById(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Conversation con = conversationService.selectConversationById(conversation);
			result.setResult(con);
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
	 * 根据贴吧名称进行查询
	 * @return
	 */
	@RequestMapping("/selectConversationByName")
	@ResponseBody
	public ResultDTO selectConversationByName(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Conversation con = conversationService.selectConversationByName(conversation);
			if(con == null){
				return new ResultDTO(MessageUtil.MSG_CONVERSAION_NOT_EXISTENT,false,null);
			}
			return new ResultDTO(MessageUtil.MSG_QUERY_SUCCESS,true,con);
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
	 * 添加用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/addConversationFollow")
	@ResponseBody
	public ResultDTO addConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.addConversationFollow(userFollowConversation);
			result.setMessage(MessageUtil.MSG_FOLLOW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_FOLLOW_ERROR);
			return result;
		}
	}
	
	/**
	 * 取消用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/deleteConversationFollow")
	@ResponseBody
	public ResultDTO deleteConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.deleteConversationFollow(userFollowConversation);
			result.setMessage(MessageUtil.MSG_UNKONW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_UNKONW_ERROR);
			return result;
		}
	}
	/**
	 * 查询用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/selectConversationFollow")
	@ResponseBody
	public ResultDTO selectConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			List<UserFollowConversation> ufc = conversationService.selectConversationFollow(userFollowConversation);
			result.setResult(ufc);
			result.setMessage(MessageUtil.MSG_UNKONW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_UNKONW_ERROR);
			return result;
		}
	}
	
	/**
	 * 查询贴吧的一些统计数据，如关注人数，发贴数等数据
	 * @return
	 */
	@RequestMapping("/selectConversationStatistics")
	@ResponseBody
	public ResultDTO selectConversationStatistics(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Map<String,Object> conversations = conversationService.selectConversationStatistics(conversation);
			result.setResult(conversations);
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
	
	/**
	 * 查询贴吧数据，按用户关注量查询指定数量的贴吧数据
	 * @param limit 要查询的贴吧数量
	 * @return
	 */
	@RequestMapping("/selectMaxFollow")
	@ResponseBody
	public ResultDTO selectMaxFollow(Integer limit){
		ResultDTO result = new ResultDTO();
		try{
			List<Conversation> selectMaxFollow = conversationService.selectMaxFollow(limit);
			result.setResult(selectMaxFollow);
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
	
	/**
	 * 查询贴吧的类型以后类型下对应的贴吧数据
	 * @param limit 要查询的贴吧数量
	 * @return
	 */
	@RequestMapping("/selectConversationTypeAndData")
	@ResponseBody
	public ResultDTO selectConversationTypeAndData(){
		ResultDTO result = new ResultDTO();
		try{
			Map<String,List<Object>> selectConversationTypeAndDatas = conversationService.selectConversationTypeAndData();
			result.setResult(selectConversationTypeAndDatas);
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
	
	/**
	 * 根据用户id查询用户关注的贴吧下的贴子最新动态
	 * @param user
	 * @return
	 */
	@RequestMapping("/selectUserFollowConversation")
	@ResponseBody
	public ResultDTO selectUserFollowConversation(User user){
		ResultDTO result = new ResultDTO();
		try{
			List<Map<String, Object>> selectUserFollowConversation = conversationService.selectUserFollowConversation(user);
			result.setResult(selectUserFollowConversation);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setResult(e.getMessage());
			e.printStackTrace();
			return result;
		}
	}
	
	/**
	 * 查询吧主相关的数据
	 * @param user
	 * @return
	 */
	@RequestMapping("/selectConversationMaster")
	@ResponseBody
	public ResultDTO selectConversationMaster(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Map<String, Object> selectConversationMaster = conversationService.selectConversationMaster(conversation);
			result.setResult(selectConversationMaster);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setResult(e.getMessage());
			e.printStackTrace();
			return result;
		}
	}
}
