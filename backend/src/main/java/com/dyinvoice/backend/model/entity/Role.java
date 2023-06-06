package com.dyinvoice.backend.model.entity;


<<<<<<< HEAD
import lombok.Data;
=======
import lombok.*;

>>>>>>> 1d61826 (DYIN-24)

import javax.persistence.*;

@Data
@Entity
<<<<<<< HEAD
=======
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 1d61826 (DYIN-24)
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    private String shortName;
}
