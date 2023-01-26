package com.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/home")
public class FileUploadController implements ServletContextAware{
	
     private ServletContext servletContext;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploads(@RequestParam ("files")MultipartFile[] files){
		try {
			System.out.println("File List :");
			for(MultipartFile file :files) {
				System.out.println("File Name: --"+file.getOriginalFilename());
				System.out.println("File Size: --"+file.getSize());
				System.out.println("File Type: --"+file.getContentType());
				System.out.println("-----------------------------------------------------");
				save(file);
			}
			return new ResponseEntity<String>("Successfully Uploaded Files....!",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> (HttpStatus.BAD_REQUEST);
		}
	}
	
	public String save(MultipartFile file) {
		try {
			SimpleDateFormat simpleDateFormat= new SimpleDateFormat("ddMMyyyyHHmmss");
			String newFileName= simpleDateFormat.format(new java.util.Date()) +file.getOriginalFilename();
			byte bytes[]= file.getBytes();
			Path path= Paths.get(this.servletContext.getRealPath("/Uploads/Images")+ newFileName);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void setServletContext( ServletContext servletContext) {
		this.servletContext=servletContext;
	}

}
