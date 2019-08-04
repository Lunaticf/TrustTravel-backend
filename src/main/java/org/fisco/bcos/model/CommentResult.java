package org.fisco.bcos.model;

import lombok.Data;

@Data
public class CommentResult {
    private String addr;
    private int index;
    private String content;
    private int score;
}
