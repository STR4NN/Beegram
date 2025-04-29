package com.example.linkedin.dto;

import com.example.linkedin.model.PostModel;
import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;

public record CommentsRequest(String content){

}
