package com.consistentlearners.azureDataLakeIntegration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.file.datalake.DataLakeDirectoryClient;
import com.azure.storage.file.datalake.DataLakeFileClient;
import com.azure.storage.file.datalake.DataLakeServiceClient;
import com.consistentlearners.azureDataLakeIntegration.configuration.ADLSConfig;

@RestController
@RequestMapping("/adlswriter")
public class adlsController {

	@Value("${adls.account-name}")
	private String accountName;
	
	@Value("${adls.account-key}")
	private String accountKey;
	
	@Value("${adls.container-name}")
	private String container;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		DataLakeServiceClient dataLakeServiceClient = ADLSConfig.GetDataLakeServiceClient(accountName, accountKey);
		DataLakeDirectoryClient dataLakeDirectoryClient = ADLSConfig.CreateDirectory(dataLakeServiceClient,
				container);
		DataLakeFileClient fileClient = dataLakeDirectoryClient.createFile(file.getOriginalFilename());
		long fileSize = file.getSize();
		fileClient.append(file.getInputStream(), 0, fileSize);
		fileClient.flush(fileSize);
	}
	
}
