package com.chacetech.common.service;

import com.chacetech.common.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndReplaceOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MspSequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Autowired
    public MspSequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence() {
        Query query = new Query().addCriteria(Criteria.where("mspId").is("managed_service_provider_sequence"));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true).upsert(true);
        DatabaseSequence counter = mongoOperations.findAndModify(query,
                update, findAndModifyOptions, DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}