package org.daniel.managementspringapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    // May change this to an enum or another table later
    private String status;
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date createDate;

    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date lastUpdateDate;

    public Date getCreateDate() {
        return (createDate != null) ? new Date(createDate.getTime()) : null;
    }

    public Date getLastUpdateDate() {
        return (lastUpdateDate != null) ? new Date(lastUpdateDate.getTime()) : null;
    }

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
        lastUpdateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }
}

