package com.as.model;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder(setterPrefix = "with")
@Getter
public class Model {
    private String name;
    private List<String> soundsLike;
}
