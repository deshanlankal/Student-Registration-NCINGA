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
    public List<Student> findByText(String text, int page, int size) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection("students");

        List<Document> pipeline = new ArrayList<>();

        // MongoDB Atlas Search stage
        pipeline.add(new Document("$search",
                new Document("text",
                        new Document("query", text)
                                .append("path", Arrays.asList("firstName", "lastName", "email", "course", "phoneNumber", "address")))));

        // Optional: Sort by something (like DOB or date added)
        pipeline.add(new Document("$sort", new Document("dob", 1)));

        // Pagination
        pipeline.add(new Document("$skip", page * size));
        pipeline.add(new Document("$limit", size));

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
                student.setImageData(img.getString("data").getBytes());
                student.setImageType(img.getString("type"));
                student.setImageName(img.getString("name"));
            }

            students.add(student);
        }

        return students;
    }
}
