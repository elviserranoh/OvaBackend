package com.university.discretas.service;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Log
@Service
public class UploadFileServiceImpl implements IUploadFileService {

	@Value("${upload.directory}")
	private String uploadDirectory;

	@Override
	public Path getPath(String fileName) {
		return Paths.get(uploadDirectory).resolve(fileName).toAbsolutePath();
	}

	@Override
	public boolean delete(String fileName) {
		if (fileName != null && fileName.length() > 0) {
			Path pathPrevious = getPath(fileName);
			File filePrevious = pathPrevious.toFile();
			if (filePrevious.exists() && filePrevious.canRead()) {
				return filePrevious.delete();
			}
		}
		return false;
	}

	@Override
	public String copy(MultipartFile file) throws Exception {
		String fileName = UUID.randomUUID() + "-" + String.valueOf(new Date().getTime()) + "." + getExtension(Objects.requireNonNull(file.getContentType()));
		Path filePath = getPath(fileName);
		Files.copy(file.getInputStream(), filePath);
		return fileName;
	}

	@Override
	public Resource load(String fileName) throws MalformedURLException {
		Path filePath = getPath(fileName);
		return new UrlResource(filePath.toUri());
	}

	@Override
	public String getExtension(String mime) throws Exception {
		switch (mime) {
			case "image/png":
				return "png";
			case "image/jpeg":
				return "jpg";
			case "application/pdf":
				return "pdf";
			case "application/msword":
				return "doc";
			case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
				return "docx";
			case "application/application/vnd.openxmlformats-officedocument.wordprocessingml.template":
				return "dotx";
			case "application/vnd.ms-excel":
				return "xls";
			case " application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				return "xlsx";
			case "application/vnd.ms-powerpoint":
				return "ppt";
			case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
				return "pptx";
			default:
				throw new Exception("Extension no permitida");
		}
	}

}
