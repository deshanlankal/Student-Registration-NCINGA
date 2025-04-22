package com.ava.studentenroll.repository;

import com.ava.studentenroll.model.Student;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SearchRepositoryImplementation implements StudentSearchRepository {

    @Autowired
    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    public List<Student> findByText(String text) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection("students");

        List<Document> pipeline = new ArrayList<>();

        // MongoDB Atlas full-text search on selected fields
        pipeline.add(new Document("$search",
                new Document("text",
                        new Document("query", text)
                                .append("path", Arrays.asList("firstName", "lastName", "email", "address")))));

        List<Student> students = new ArrayList<>();
        for (Document doc : collection.aggregate(pipeline)) {
            Student student = new Student();

            student.setId(doc.getObjectId("_id").toString());
            student.setFirstName(doc.getString("firstName"));
            student.setLastName(doc.getString("lastName"));
            student.setEmail(doc.getString("email"));
            student.setCourse(doc.getString("course"));
            student.setPhoneNumber(doc.getString("phoneNumber"));
            student.setAddress(doc.getString("address"));
            student.setDob(doc.getString("dob"));
            student.setGender(doc.getString("gender"));

            // Optional image mapping
            if (doc.containsKey("imageData")) {
                Document img = (Document) doc.get("imageData");
                if (img != null) {
                    student.setImageName(img.getString("name"));
                    student.setImageType(img.getString("type"));
                    String base64 = img.getString("data");
                    if (base64 != null) {
                        student.setImageData(base64.getBytes());
                    }
                }
            }

            students.add(student);
        }

        return students;
    }
}
