package br.com.gabriel.api.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "request_files")
public class RequestFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "text", nullable = false)
    private String location;

    @Getter(onMethod = @__({@JsonIgnore}))
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;
}
