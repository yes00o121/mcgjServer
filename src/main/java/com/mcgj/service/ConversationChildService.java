package com.mcgj.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.UserCollectionConversationChild;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PageUtil;

/**
 * 贴子业务层
 * @author 杨晨
 *
 */
@Service
public class ConversationChildService implements IConversationChildService{
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;
	
	@Autowired
	private IUserService userService;

	public void delete(Integer id) {
		
	}

	public void insert(ConversationChild record) {
		
	}

	public void update(ConversationChild record) {
		
	}

	public ConversationChild selectById(Integer id) {
		return null;
	}
	
	/**
	 * 根据贴子id查询,获取当前帖子的第一层数据
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id) {
		//判断id是否合法
		if(id == null || "".equals(id)){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		Map<String,Object> converstationChilds = conversationChildMapper.selectConversationChildById(id);
		if(converstationChilds == null || converstationChilds.size() == 0){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		return converstationChilds;
	}

	@Override
	public Map<String,Object> selectConversationChildByConversationId(
			ConversationChild conversationChild) {
		if(conversationChild.getConversationId() == null || "".equals(conversationChild.getConversationId())){
			throw new RuntimeException("id不能为空");
		}
		Map<String,Object> maps = new HashMap<String, Object>();
		List<ConversationChild> ConversationChilds = conversationChildMapper.selectConversationChildByConversationId(conversationChild);
		maps.put("conversationChilds", PageUtil.createPage(conversationChild.getStart(),conversationChild.getLimit(),ConversationChilds));
		maps.put("size", ConversationChilds.size());
		return maps;
	}

	/**
	 * 新增帖子的方法
	 */
	public void addConversationChild(ConversationChild conversationChild) {
		conversationChild.setCreateDate(new Date());
		//判断参数合法性
		if(conversationChild.getConversationId() == null || "".equals(conversationChild.getConversationId())){
			throw new RuntimeException("id不能为空");
		}
		if(conversationChild.getUserId() == null || "".equals(conversationChild.getUserId())){
			throw new RuntimeException("用户id不能为空");
		}
		if(conversationChild.getTitle() == null || "".equals(conversationChild.getTitle())){
			throw new RuntimeException("标题不能为空");
		}
		if(conversationChild.getContent() == null || "".equals(conversationChild.getContent())){
			throw new RuntimeException("内容不能为空");
		}
		conversationChildMapper.insert(conversationChild);//插入帖子
		ConversationChildChild ccc = new ConversationChildChild();
		ccc.setConversationChildId(conversationChild.getId());
		ccc.setUserId(conversationChild.getUserId());
		ccc.setConversationChildId(conversationChild.getId());
		ccc.setContent(conversationChild.getContent());
		ccc.setCreateDate(new Date());
		ccc.setIsManage(1);//楼主
		ccc.setIsLook(1);
		conversationChildChildMapper.insert(ccc);//将帖子的内容插入楼层中
	}
	
	/**
	 * 新增收藏方法
	 */
	public void addConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		conversationChildMapper.addConversationChildCollection(userCollectionConversationChild);
	}

	@Override
	public void deleteConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		int num = conversationChildMapper.deleteConversationChildCollection(userCollectionConversationChild);
		if(num == 0){//判断删除条数，如果等于0没有找到可以删除的数据，抛出异常
			throw new RuntimeException(MessageUtil.MSG_COLLECTION_ERROR);
		}
	}

	@Override
	public UserCollectionConversationChild selectConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		return conversationChildMapper.selectConversationChildCollection(userCollectionConversationChild);
	}
	//参数合法性判断,收藏的增删查公用
	private void paramsJudge(UserCollectionConversationChild userCollectionConversationChild){
		//判断参数合法性
		if(userCollectionConversationChild.getUserId() == null || "".equals(userCollectionConversationChild.getUserId())){
			throw new RuntimeException("用户id不能为空");
		}
		//判断参数合法性
		if(userCollectionConversationChild.getConversationChildId() == null || "".equals(userCollectionConversationChild.getConversationChildId())){
			throw new RuntimeException("贴子id不能为空");
		}
	}

	/**
	 * 查询用户所发布的贴子 
	 * @return
	 */
	public Map<String,Object> selectUserPublishConversationChild(
			ConversationChild conversationChild) {
		//判断参数是否合法
		if(conversationChild.getUserId() == null || "".equals(conversationChild.getUserId()))
			throw new RuntimeException("用户id不能为空");
		List<ConversationChild> selectUserPublishConversationChild = conversationChildMapper.selectUserPublishConversationChild(conversationChild);
		List<ConversationChild> list = PageUtil.createPage(conversationChild.getStart(),conversationChild.getLimit(), selectUserPublishConversationChild);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("total",selectUserPublishConversationChild.size());
		map.put("conversationChilds",list);
		return map;
	}

	/**
	 * 查询对应天数内最为活跃的贴吧排名
	 */
	public List<ConversationChild> selectMaxConversationChildByDay(Integer day) {
		if(day == null)
			throw new RuntimeException("天数不能为空");
		//根据天数获取时间区间
		long millisecond = day*24*60*60*1000;//得到指定天的毫秒数
		Date startTime = new Date(new Date().getTime()-millisecond);//获取指定天数前的时间
		Date endTime = new Date();//当前时间
		List<ConversationChild> selectMaxConversationChildByDay = conversationChildMapper.selectMaxConversationChildByDay(startTime, endTime);
		return selectMaxConversationChildByDay;
	}

	/**
	 * 添加楼层数据，爬虫专用
	 */
	public void addFloorDataSpider(ConversationChildChild conversationChildChild,Integer userId2) {
	
		if(conversationChildChild.getUserId().equals(userId2)){
			conversationChildChild.setIsManage(1);
		}else{
			conversationChildChild.setIsManage(0);
		}
		conversationChildChildMapper.insert(conversationChildChild);
	}
}
