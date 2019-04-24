package com.mcgj.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.dao.UserMapper;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.MessageType;
import com.mcgj.entity.User;
import com.mcgj.entity.loginLog;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.security.MD5Util;
import com.mcgj.utils.Base64Util;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.web.websocket.Message;

@Service
public class UserService implements IUserService{
	
	private String yan = "yangchen";//盐

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	/**
	 * 登录方法
	 */
	public User login(User user) {
		//判断数据合法性
		if(!verificationCodeCheck(user)){//校验验证码是否正确
			throw new RuntimeException("验证码错误");
		}
		if(user.getAccount() == null || "".equals(user.getAccount())){
			throw new RuntimeException("账号不能为空");
		}
		if(user.getPassword() == null || "".equals(user.getPassword())){
			throw new RuntimeException("密码不能为空");
		}
		//解密
		String dcodePwd = Base64Util.dcode(user.getPassword());
		//获取密码加盐后使用Md5进行加密
		user.setPassword(MD5Util.getMD5((yan.concat(dcodePwd)).getBytes()));
		User record = userMapper.login(user);//查询用户数据
		if(record == null){
			throw new RuntimeException("用户不存在或密码错误");
		}
		user.setId(record.getId());
		//登录后生成用户唯一的token，写入缓存中
		String auth = MD5Util.getMD5((user.getAccount()+""+user.getPassword()).getBytes());
		record.setToken(auth);//将token返回给用户，作为唯一认证
		RedisHashUtil.setex(auth,record,PropertiesUtil.getLoginDelay());//设置用户会话时长
		insertLogin(user);//记录用户登录日志 
		return record;
	}
	/**
	 * 记录用户登录日志
	 */
	private void insertLogin(User user){
		loginLog loginLog = new loginLog();
		loginLog.setUserId(user.getId());
		loginLog.setCreateDate(new Date());
		loginLog.setOs(user.getOs());
		loginLog.setBrowser(user.getBrowser());
		loginLog.setLoginIp(user.getIp());
		userMapper.insertLoginLog(loginLog);
		
	}
	/**
	 * 验证用户输入验证码是否正常
	 * @return
	 */
	private Boolean verificationCodeCheck(User user){
		String verificationCode=  user.getVerificationCode();//获取浏览器验证code
		if(verificationCode == null || "".equals(verificationCode)){
			throw new RuntimeException("出现异常，请联系管理员");
		}
		if(user.getCheckCode() == null || "".equals(user.getCheckCode())){
			throw new RuntimeException("验证码不能为空");
		}
		String code = (String)RedisHashUtil.get(verificationCode);//获取对应的验证码
		if(code ==null){
			throw new RuntimeException("验证超时");
		}
		if(code.equals(user.getCheckCode())){
			return true;
		}
		return false;
	}

	
	/**
	 * 用户注册方法
	 */
	public void register(User user) {
		//数据合法性判断
		if(user.getAccount() == null || "".equals(user.getAccount())){
			throw new RuntimeException("账号不能为空");
		}
		if(user.getPassword() == null || "".equals(user.getPassword())){
			throw new RuntimeException("密码不能为空");
		}
		if(user.getUserName() == null || "".equals(user.getUserName())){
			throw new RuntimeException("用户名不能为空");
		}
		if(user.getPhoto() == null || "".equals(user.getPhoto())){
			throw new RuntimeException("头像不能为空");
		}
		//判断账号是否存在
		User record = this.findUserByAccount(user.getAccount());
		if(record != null){
			throw new RuntimeException("账号已存在");
		}
		//对密码进行加密
		user.setPassword(MD5Util.getMD5(yan.concat(user.getPassword()).getBytes()));
		this.insert(user);//将数据插入user表
	}
	/**
	 * 上传图片接口
	 * @return
	 */
	public String upload(){
		return null;
	}

	public void delete(Integer id) {
		
	}
	/**
	 * 插入方法
	 */
	public void insert(User record) {
		this.userMapper.insert(record);
	}

	public void update(User record) {
		
	}

	public User selectById(Integer id) {
		
		return null;
	}
	
	/**
	 * 根据用户账号查询用户数据
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account) {
		User user = this.userMapper.findUserByAccount(account);
		return user;
	}

	@Override
	public List<Message> selectUserUnreadMessageCountByUserId(Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("用户id不能为空");
		}
		List<Message> messages = new ArrayList<Message>();
		Integer replyNumber = userMapper.selectUserUnreadMessageCountByUserId(userId);//获取用户未查看的回复消息
		messages.add(new Message(replyNumber, MessageType.reply));
		return messages;
	}

	public List<ConversationChild> selectCollectionConversationChildByUserId(
			Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("用户id不能为空");
		}
		
		return conversationChildMapper.selectCollectionConversationChildByUserId(userId);
	}
	/**
	 * 爬虫****
	 * 判断用户是否存在，不存在进行创建，然后返回用户信息
	 */
	public Integer selectIsExists(String userName,String photo) {
		Integer selectIsExists = userMapper.selectIsExists(userName);
		if(selectIsExists != null){
			return selectIsExists;
		}
		//用户不存在，进行数据插入
		User record = new User();
		record.setUserName(userName);
		record.setPhoto(photo);
		record.setAccount(userName);
		record.setPassword("e10adc3949ba59abbe56e057f20f883e");//默认123456
		record.setAdmin(false);
		userMapper.insert(record);
		return record.getId();
	}
}	
