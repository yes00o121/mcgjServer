package com.mcgj.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mcgj.utils.StringUtil;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class MongoDBRemoteFileService implements IRemoteFileMongoSer{
	@Autowired
	private MongoTemplate mongoTemplate3;

	public String upload(InputStream in) throws IOException {
		GridFSFile gfsf = getGridFS().createFile(in);
		gfsf.save();
		return gfsf.getId().toString();
	}

	public GridFSDBFile find(String fileId) throws IOException {
		GridFSDBFile gfsf = getGridFS().findOne(new ObjectId(fileId));
		return gfsf;
	}
	
	public void download(String mongoid, OutputStream out) throws IOException {
		try{
		    if(StringUtil.isEmpty(mongoid)){
		        return ;
		    }
			GridFSDBFile gfsf = getGridFS().findOne(new ObjectId(mongoid));
			if (gfsf != null) {
				gfsf.writeTo(out);
			}
		}catch(Exception e){
			throw new RuntimeException("mongoid:"+mongoid,e);
		}

	}

	public void delete(String filePath) throws IOException {
		getGridFS().remove(new ObjectId(filePath));
	}

	private GridFS getGridFS() {
		DB db = mongoTemplate3.getDb();
		return new GridFS(db);
	}

	public String upload(byte[] data) throws IOException {
		GridFSFile gfsf = getGridFS().createFile(data);
		gfsf.save();
		return gfsf.getId().toString();
	}
}
