package vn.iotstar.demo.springboot3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Categories")
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(name = "category_name", length = 200, columnDefinition = "nvarchar(200) not null")
        private String categoryname;
        @Column(name = "category_img", length = 500, columnDefinition = "nvarchar(500)")
        private String image;

}

