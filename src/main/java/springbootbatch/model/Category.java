package springbootbatch.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Lightning on 8/6/2015.
 */
@Entity(name = "tbl_category_code")
@Data
public class Category {
    @Id
    @Column(name = "category_code")
    String categoryCode;

    @Column(name = "category_name")
    String categoryName;

    @Column(name = "seq")
    String seq;

    @Column(name = "note")
    String note;
}
