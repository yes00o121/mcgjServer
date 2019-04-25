package com.mcgj.spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.dao.ConversationMapper;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.redis.RedisStrUtil;
import com.mcgj.service.IConversationChildService;
import com.mcgj.service.IFileService;
import com.mcgj.service.IUserService;
import com.mcgj.utils.HttpClientUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.StringUtil;
import com.mcgj.web.controller.AbstractBaseController;
import com.mcgj.web.dto.ResultDTO;

/**
 * 爬虫控制器
 * @author ad
 *
 */
@Controller
@RequestMapping("/spider")
public class SpiderController extends AbstractBaseController{
		
	Logger log = Logger.getLogger(SpiderController.class);
	
	@Autowired
	IConversationChildService conversationChildService;
	
	@Autowired
	private ConversationMapper conversationMapper;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	@Autowired
	private IFileService fileService;
	
	/**
	 * 查询对应天数内最为活跃的贴吧排名
	 * @param limit 要查询的贴吧数量
	 * @return
	 */
	@RequestMapping("/addFloorDataSpider")
	@ResponseBody
	public ResultDTO addFloorDataSpider(ConversationChildChild conversationChildChild,Integer userId2){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.addFloorDataSpider(conversationChildChild,userId2);
//			List<ConversationChild> selectMaxConversationChildByDays = conversationChildService.selectMaxConversationChildByDay(day);
//			result.setResult(selectMaxConversationChildByDays);
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
	 * 插入贴吧数据
	 */
	@RequestMapping(value = "/addConversation" , method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO addConversation(Conversation conversation){
		try {
			//判断贴吧是否存在,存在不进行插入操作,在判断其他数据是否变更,变更了更新当前记录
			Conversation con = conversationMapper.selectConversation(conversation);
			if(con != null){
				conversation.setId(con.getId());//设置主键
				//头像和横幅已经本地化,无从得知原先的地址是多少,目前只能通过签名来判断贴吧更新,就是改了头像和横幅的时候检测不到贴吧基本信息的变更
				if(StringUtil.isNotEmpty(conversation.getAutograph()) && !conversation.getAutograph().equals(con.getAutograph())){
					//将头像和横幅本地化
					if(StringUtil.isNotEmpty(conversation.getPhoto())){
						String photo = fileService.upLoadRemoteFile(conversation.getPhoto());
						con.setPhoto(photo);
					}else{
						con.setPhoto(null);
					}
					if(StringUtil.isNotEmpty(conversation.getCardBanner())){
						String cardBanner = fileService.upLoadRemoteFile(conversation.getCardBanner());
						con.setCardBanner(cardBanner);
					}else{
						con.setCardBanner(null);
					}
					con.setModifyDate(new Date());
					con.setAutograph(conversation.getAutograph());
					conversationMapper.update(con);
				}
			}else{
				//将头像和横幅本地化
				if(StringUtil.isNotEmpty(conversation.getPhoto())){
					String photo = fileService.upLoadRemoteFile(conversation.getPhoto());
					conversation.setPhoto(photo);
				}else{
					conversation.setPhoto(null);
				}
				if(StringUtil.isNotEmpty(conversation.getCardBanner())){
					String cardBanner = fileService.upLoadRemoteFile(conversation.getCardBanner());
					conversation.setCardBanner(cardBanner);
				}else{
					conversation.setCardBanner(null);
				}
				conversation.setCreateUserId(1);
				conversation.setCurrentManageUserId(1);
				conversationMapper.insert(conversation);// 插入贴吧
			}
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, true, conversation);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_INSERT_FAILED, false, null);
		}
		
	}
	
	@RequestMapping("/selectConversation")
	@ResponseBody
	public ResultDTO selectConversation(){
		try {
			return new ResultDTO(MessageUtil.MSG_QUERY_SUCCESS, true, conversationMapper.selectConversation(null));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_QUERY_ERROR, false, null);
		}
		
	}
	
	@RequestMapping("/selectUserByAccount")
	@ResponseBody
	public User getUserByAccount(String account){
		return userService.findUserByAccount(account);
	}
	
	/**
	 * 插入贴子数据
	 * @return
	 */
	@RequestMapping(value = "/addConversationChild", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO insertConversationChild(ConversationChild conversationChild){
		try {
			Object result = RedisHashUtil.get(conversationChild.getTitle() + conversationChild.getConversationId() + conversationChild.getUserId());
			if(result == null){
				//判断贴子在该贴吧是否存在,存在直接返回,不存在插入数据
				ConversationChild cc = conversationChildMapper.selectConversationChild(conversationChild);
				if(cc != null){
					RedisStrUtil.set(conversationChild.getTitle() + conversationChild.getConversationId() + conversationChild.getUserId() + "", JSONObject.toJSONString(cc));
					return new ResultDTO(MessageUtil.MSG_UNKONW_SUCCESS, true, cc);
				}else{
					//数据库也找不到对应数据,插入数据库,并且添加数据缓存
					conversationChildMapper.insert(conversationChild);//插入帖子
					RedisStrUtil.set(conversationChild.getTitle() + conversationChild.getConversationId() + conversationChild.getUserId() + "", JSONObject.toJSONString(conversationChild));
				}
			}
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS,true,conversationChild);
		} catch (Exception e) {
			log.error(e);
			return new ResultDTO(MessageUtil.MSG_INSERT_FAILED,false,null);
		}
	}
	
	/**
	 * 查询贴子数据
	 * @param conversationChild
	 * @return
	 */
	@RequestMapping("/selectConversationChild")
	@ResponseBody
	public ConversationChild selectConversationChild(ConversationChild conversationChild){
		return conversationChildMapper.selectConversationChild(conversationChild);
	}
	
	/**
	 * 插入贴吧楼层
	 * @return
	 */
	@RequestMapping("/addConversationChildChild")
	@ResponseBody
	public ResultDTO addConversationChildChild(ConversationChildChild conversationChildChild,String time){
		try {
			//判断这条楼层数据是否存在,不存在插入数据，不然直接退出
			conversationChildChild.setCreateDate(new Date(Long.parseLong(time)));
			ConversationChildChild ccc = conversationChildChildMapper.selectConversationChildChild(conversationChildChild);
			if(ccc != null){
				return new ResultDTO(MessageUtil.MSG_UNKONW_SUCCESS,true,null);
			}
			//判断用户是否是楼主
			ConversationChild cc = new ConversationChild();
			cc.setUserId(conversationChildChild.getUserId());
			cc.setId(conversationChildChild.getConversationChildId());
			cc = conversationChildMapper.selectConversationChild(cc);
			if(cc != null){
				//是楼主
				conversationChildChild.setIsManage(1);
			}else{
				//不是楼主
				conversationChildChild.setIsManage(0);
			}
			conversationChildChild.setCreateDate(new Date(Long.parseLong(time)));//转换时间
			conversationChildChildMapper.insert(conversationChildChild);//插入楼层
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, false, null);
		}
	}
	
	/**
	 * 注册,返回主键
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Integer register(User user){
		//获取用户名称判断是否存在,存在直接返回,反之插入数据
		return userService.selectIsExists(user.getAccount(), user.getPhoto());
	}
	
	public static void main(String[] args) {
		InputStream fileInputStream = HttpClientUtil.getFileInputStream("http://imgsrc.baidu.com/forum/pic/item/267f9e2f0708283852d72df4b099a9014d08f17c.jpg");
		try {
			System.out.println(fileInputStream.available());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
