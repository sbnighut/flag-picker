package com.practice.flagpicker.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by snighut on 2/22/20.
 */
@Data
@Document(collection = "countrycollection", collation = "")
//@Builder
@NoArgsConstructor
public class Country {
    //--- Fields from DB ---
    @Id
    @JsonIgnore
    public ObjectId id;

    @Indexed(unique = true)
    public String name;

    String flag;

    String continent;

    public String getName() {
        return this.name;
    }

    public String getFlag() {
        return this.flag;
    }

    public String getContinent(){
        return this.continent;
    }

    public Country(String name, String flag, String continent) {
        this.name = name;
        this.flag = flag;
        this.continent = continent;
    }
}
