package com.devspark.antifakenews.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * News entity representing news articles
 * * @author DevSpark Team
 * @version 1.0.0
 */
@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class News extends BaseEntity {

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "short_detail", nullable = false, length = 500)
    private String shortDetail;

    @Column(name = "full_detail", nullable = false, columnDefinition = "TEXT")
    private String fullDetail;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private NewsStatus status = NewsStatus.UNDECIDED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;


    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Vote> votes = new ArrayList<>();

    @Embedded
    @Builder.Default
    private VoteCount voteCount = new VoteCount();

    public enum NewsStatus {
        FAKE, NOT_FAKE, UNDECIDED
    }

    @Builder.Default
    private boolean softDeleted = false;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCount {
        @Column(name = "fake_votes")
        @Builder.Default
        private Integer fakeVotes = 0;

        @Column(name = "not_fake_votes")
        @Builder.Default
        private Integer notFakeVotes = 0;

        public Integer getTotalVotes() {
            return fakeVotes + notFakeVotes;
        }

        public Double getFakePercentage() {
            if (getTotalVotes() == 0) return 0.0;
            return (double) fakeVotes / getTotalVotes() * 100;
        }

        public Double getNotFakePercentage() {
            if (getTotalVotes() == 0) return 0.0;
            return (double) notFakeVotes / getTotalVotes() * 100;
        }
    }
}