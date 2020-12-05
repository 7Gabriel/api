package br.com.gabriel.api.domain;

import br.com.gabriel.api.enums.RequestState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.aspectj.lang.annotation.Before;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 75, nullable = false)
    private String subject;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name= "creation_date",nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(length = 12, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestState state;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Getter(onMethod = @__({@JsonIgnore}))
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<RequestStage> stages = new ArrayList<>();

    @Getter(onMethod = @__({@JsonIgnore}))
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<RequestFile> files = new ArrayList<>();
}
