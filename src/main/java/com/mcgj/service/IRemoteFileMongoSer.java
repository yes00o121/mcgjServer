package com.mcgj.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * mongo接口
 * @author ad
 *
 */
	
public interface IRemoteFileMongoSer {
	String upload(InputStream in) throws IOException;

	String upload(byte[] data) throws IOException;

	void download(String filePath, OutputStream out)
			throws IOException;

	void delete(String filePath) throws IOException;

	GridFSDBFile find(String fileId) throws IOException;
}
