package com.learningstuff.springbatch.models;

import lombok.*;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Entity
@Table(name = "sample")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Sample {

    @Id
    private long id;

    @Lob
    @Column(length = 65535)
    private String descriptions;

    @Column(length = 255)
    private String name;

    private double value;

}
