package com.mcgj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.MessageType;
import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.security.MD5Util;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PageUtil;
import com.mcgj.web.websocket.Message;
import com.mcgj.web.websocket.WebSocketServer;

/**
 * 楼层业务层 
 * @author ad
 *
 */
@Service
public class ConversationChildChildService implements IConversationChildChildService{

	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	public void delete(Integer id) {
		
	}

	public void insert(ConversationChildChild record) {
		
	}

	public void update(ConversationChildChild record) {
		
	}

	public ConversationChildChild selectById(Integer id) {
		return null;
	}
	
	/**
	 * 根据贴子id查询其下的楼层数据
	 * @return
	 */
	public Map<String,Object> selectCCCByCCId(ConversationChildChild conversationChildChild){
		//判断参数合法性
		if(conversationChildChild.getConversationChildId() == null || "".equals(conversationChildChild.getConversationChildId())){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		if(conversationChildChild.getSingleFloor() == null || "".equals(conversationChildChild.getSingleFloor())){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		List<Map<String,Object>> conversationChildChilds  = conversationChildChildMapper.selectCCCByCCId(conversationChildChild);//当前帖子的所有楼层数据
		
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("conversationChildChilds", PageUtil.createPage(conversationChildChild.getStart(),conversationChildChild.getLimit(),conversationChildChilds));
		maps.put("size", conversationChildChilds.size());
		return maps;
	}
	
	/**
	 * 新增楼层方法
	 */
	@Transactional
	public void addConversationChildChild(ConversationChildChild conversationChildChild) {
		//如果是楼主新增的楼层，楼主查看状态默认为1,不然将消息推送给楼主
		if(conversationChildChild.getIsManage().equals(1)){
			conversationChildChild.setIsLook(1);//楼主回复的帖子，不需要进行消息的推送
		}
//		conversationChildChild.setContent(Base64Util.getStrBASE64(conversationChildChild.getContent()));//对内容文本进行加密
		conversationChildChildMapper.insert(conversationChildChild);//插入楼层
		if(conversationChildChild.getIsManage().equals(0)){//不是楼主回复的贴子，将消息推送给楼主
			if(conversationChildChild.getConversationChildId() != null && !"".equals(conversationChildChild.getConversationChildId())){
				User user = conversationChildMapper.selectUserByconversationChildId(conversationChildChild.getConversationChildId());
				if(user != null){
					String token = MD5Util.getMD5((user.getAccount()+user.getPassword()).getBytes());//获取楼主token
					if(RedisHashUtil.get(token) != null){//判断楼主会话是否存在，不存在不进行消息的推送
						WebSocketServer.sendMessage(token,new Message(MessageUtil.MSG_REPLY_FLOOR,MessageType.reply));
					}
				}
			}
		}
	}

	@Override
	public Map<String,Object> selectConversationChildChildReplyByUserId(
			ConversationChildChild conversationChildChild) {
		if(conversationChildChild.getUserId() == null){
			throw new RuntimeException("用户id不能为空");
		}
		List<ConversationChildChild> ccc = conversationChildChildMapper.selectConversationChildChildReplyByUserId(conversationChildChild.getUserId());//获取所有的数据
		//对数据进行分页
		List<ConversationChildChild> list = PageUtil.createPage(conversationChildChild.getStart(),conversationChildChild.getLimit(), ccc);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total",ccc.size());//设置总数
		map.put("conversationChildChilds", list);
		return map;
	}

	/**
	 * 将该用户所有的未查看楼层都修改为查看状态
	 */
	public void updateConversationChildChildReplyByUerId(Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("用户id不能为空");
		}
		conversationChildChildMapper.updateConversationChildChildReplyByUerId(userId);
	}
	
	
}
