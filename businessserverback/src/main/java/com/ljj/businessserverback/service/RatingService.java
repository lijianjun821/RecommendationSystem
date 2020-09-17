package com.ljj.businessserverback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ljj.businessserverback.model.domain.Rating;
import com.ljj.businessserverback.model.request.ProductRatingRequest;
import com.ljj.businessserverback.utils.Constant;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RatingService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String,  String> redisTemplate;

    private MongoCollection<Document> ratingCollection;

    private MongoCollection<Document> getRatingCollection() {
        if (null == ratingCollection)
            ratingCollection = mongoTemplate.getDb().getCollection(Constant.MONGODB_RATING_COLLECTION);
        return ratingCollection;
    }

    private Rating documentToRating(Document document) {
        Rating rating = null;
        try {
            rating = objectMapper.readValue(document.toJson(), Rating.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rating;

    }

    public boolean productRating(ProductRatingRequest request) {
        Rating rating = new Rating(request.getUserId(), request.getProductId(), request.getScore());
        updateRedis(rating);
        if (ratingExist(rating.getUserId(), rating.getProductId())) {
            return updateRating(rating);
        } else {
            return newRating(rating);
        }
    }

    private void updateRedis(Rating rating) {
        
        String key = "userId:" + rating.getUserId();
        String value = rating.getProductId() + ":" + rating.getScore();
        Boolean hasKey = redisTemplate.hasKey(key);
        Long size = redisTemplate.opsForList().size(key);
        if ( hasKey &&  size >= Constant.REDIS_PRODUCT_RATING_QUEUE_SIZE) {

            //根据索引修改list中的某条数据
            redisTemplate.opsForList().set(key, size-1, value);
        }else {
            //将list放入缓存
            redisTemplate.opsForList().rightPush(key, value);
        }

    }

    private boolean newRating(Rating rating) {
        try {
            getRatingCollection().insertOne(Document.parse(objectMapper.writeValueAsString(rating)));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean ratingExist(int userId, int productId) {
        return null != findRating(userId, productId);
    }

    private boolean updateRating(Rating rating) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("userId", rating.getUserId());
        basicDBObject.append("productId", rating.getProductId());
        getRatingCollection().updateOne(basicDBObject,
                new Document().append("$set", new Document("score", rating.getScore())));
        return true;
    }

    private Rating findRating(int userId, int productId) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("userId", userId);
        basicDBObject.append("productId", productId);
        FindIterable<Document> documents = getRatingCollection().find(basicDBObject);
        if (documents.first() == null)
            return null;
        return documentToRating(documents.first());
    }

}
