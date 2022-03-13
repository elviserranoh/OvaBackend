package com.university.discretas.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {
	public Path getPath(String fileName);
	public boolean delete(String fileName) ;
	public String copy(MultipartFile file) throws Exception;
	public Resource load(String fileName) throws MalformedURLException;
	public String getExtension(String mime) throws Exception;

}
