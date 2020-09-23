package com.ljj.businessserverback.service;

import com.ljj.businessserverback.model.domain.RateMoreProducts;
import com.ljj.businessserverback.model.domain.RateMoreRecentlyProducts;
import com.ljj.businessserverback.model.recom.Recommendation;
import com.ljj.businessserverback.model.request.*;
import com.ljj.businessserverback.utils.Constant;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Recommendation> getHotRecommendations(HotRecommendationRequest request) {
        // 获取热门电影的条目
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("yearmonth"))); //排序逻辑
        query.skip(0).limit(request.getSum()); // 分页逻辑
        List<RateMoreRecentlyProducts> rateMoreRecentlyProducts = mongoTemplate.find(query, RateMoreRecentlyProducts.class, Constant.MONGODB_RATE_MORE_PRODUCTS_RECENTLY_COLLECTION);

        List<Recommendation> recommendations = new ArrayList<>();
        for(int i =0;i<rateMoreRecentlyProducts.size();i++){
            Recommendation recommendation = new Recommendation();
            recommendation.setProductId(rateMoreRecentlyProducts.get(i).getProductId());
            recommendation.setScore(0D);
            recommendations.add(recommendation);
        }
        return recommendations;
    }

    public List<Recommendation> getRateMoreRecommendations(RateMoreRecommendationRequest request) {
        // 获取评分最多电影的条目
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("count"))); //排序逻辑
        query.skip(0).limit(request.getSum()); // 分页逻辑
        List<RateMoreProducts> rateMoreProducts = mongoTemplate.find(query, RateMoreProducts.class,Constant.MONGODB_RATE_MORE_PRODUCTS_COLLECTION);

        List<Recommendation> recommendations = new ArrayList<>();
        for(int i =0;i<rateMoreProducts.size();i++){
            Recommendation recommendation = new Recommendation();
            recommendation.setProductId(rateMoreProducts.get(i).getProductId());
            recommendation.setScore(0D);
            recommendations.add(recommendation);
        }
        return recommendations;

    }

    // user recs
    public List<Recommendation> getCollaborativeFilteringRecommendations(UserRecommendationRequest request) {
        MongoCollection<Document> userRecsCollection = mongoTemplate.getDb().getCollection(Constant.MONGODB_USER_RECS_COLLECTION);
        Document document = userRecsCollection.find(new Document("userId", request.getUserId())).first();

        List<Recommendation> recommendations = new ArrayList<>();
        try {
            ArrayList<Document> recs = document.get("recs", ArrayList.class);
            for (Document recDoc : recs) {
                recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(recommendations.size()>request.getSum()){
            recommendations.sort((o1,o2)->o2.getScore().compareTo(o1.getScore()));
            return recommendations.subList(0,request.getSum());
        }
        return recommendations;
    }

    // user stream recs
    public List<Recommendation> getStreamRecommendations(UserRecommendationRequest request) {
        MongoCollection<Document> userRecsCollection = mongoTemplate.getDb().getCollection(Constant.MONGODB_STREAM_RECS_COLLECTION);
        Document document = userRecsCollection.find(new Document("userId", request.getUserId())).first();

        List<Recommendation> recommendations = new ArrayList<>();
        try {
            ArrayList<Document> recs = document.get("recs", ArrayList.class);

            for (Document recDoc : recs) {
                recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(recommendations.size()>request.getSum()){
            recommendations.sort((o1,o2)->o2.getScore().compareTo(o1.getScore()));
            return recommendations.subList(0,request.getSum());
        }
        return recommendations;
    }

    public List<Recommendation> getItemCFRecommendations(ItemCFRecommendationRequest request) {
        MongoCollection<Document> itemCFProductsCollection = mongoTemplate.getDb().getCollection(Constant.MONGODB_ITEMCF_COLLECTION);
        Document document = itemCFProductsCollection.find(new Document("productId", request.getId())).first();

        System.out.println(document.get("recs"));
        List<Recommendation> recommendations = new ArrayList<>();
        ArrayList<Document> recs = document.get("recs", ArrayList.class);

        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }

        return recommendations;
    }

    public List<Recommendation> getContentBasedRecommendations(ContentBasedRecommendationRequest request) {
        MongoCollection<Document> contentBasedProductsCollection = mongoTemplate.getDb().getCollection(Constant.MONGODB_CONTENTBASED_COLLECTION);
        Document document = contentBasedProductsCollection.find(new Document("productId", request.getId())).first();

        System.out.println(document.get("recs"));
        List<Recommendation> recommendations = new ArrayList<>();
        ArrayList<Document> recs = document.get("recs", ArrayList.class);

        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("productId"), recDoc.getDouble("score")));
        }

        return recommendations;
    }
}