package com.consistentlearners.azureDataLakeIntegration.configuration;

import org.springframework.context.annotation.Configuration;

import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.file.datalake.DataLakeDirectoryClient;
import com.azure.storage.file.datalake.DataLakeFileSystemClient;
import com.azure.storage.file.datalake.DataLakeServiceClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;

@Configuration
public class ADLSConfig {

	static public DataLakeServiceClient GetDataLakeServiceClient
	(String accountName, String accountKey){

	    StorageSharedKeyCredential sharedKeyCredential =
	        new StorageSharedKeyCredential("consistentlearners", "C5illoy8tbjsmZk/USOspDvgqD3Qq9s1qTwH+zKS5dIKCvox4IQ3tM5XRuuv67BJ9lGwQGNWLX49oaJO2rhx5Q==");

	    DataLakeServiceClientBuilder builder = new DataLakeServiceClientBuilder();

	    builder.credential(sharedKeyCredential);
	    builder.endpoint("https://" + "consistentlearners" + ".dfs.core.windows.net");

	    return builder.buildClient();
	}
	
	static public DataLakeDirectoryClient CreateDirectory
	(DataLakeServiceClient serviceClient, String fileSystemName){

	    DataLakeFileSystemClient fileSystemClient =
	    serviceClient.getFileSystemClient(fileSystemName);
	    if (fileSystemClient.getDirectoryClient("consistent/documents/pictures").exists()) {
	    	return fileSystemClient.getDirectoryClient("consistent/documents/pictures");
	    } else {
	    	return fileSystemClient.createDirectory("consistent/documents/pictures");
	    }
	    
	}
}
