package com.github.vinicius2335.back.modules.goals.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class Completion{
    private String id;
    private String title;
    private OffsetDateTime completedAt;
}